package com.example.demo.controller;

import com.example.demo.config.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NettyWebSocketServer {

    // 接收连接线程组
    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    // 处理读写线程组
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public static final int WS_PORT = 8081;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                   ChannelPipeline pipeline = ch.pipeline();
                                // Http编解码器，websocket先要http握手
                                pipeline.addLast(new HttpServerCodec());
                                // 分块写入
                                pipeline.addLast(new ChunkedWriteHandler());
                                // http聚合，最大接收8M
                                pipeline.addLast(new HttpObjectAggregator(8192 * 1024));
                                // WebSocket 握手处理器，访问路径 /
                                pipeline.addLast(new io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler("/"));
                                // 自己的业务处理器
                                pipeline.addLast(new WebSocketHandler());
                            }
                        });

                ChannelFuture future = bootstrap.bind(WS_PORT).sync();
                System.out.println("Netty‑WebSocket 启动成功，端口：" + WS_PORT);
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    // 项目关闭时释放netty线程
    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
