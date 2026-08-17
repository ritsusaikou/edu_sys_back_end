package com.example.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.demo.config.MyClientHandler;
import com.example.demo.entity.vo.Result;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SaCheckLogin
@RequestMapping("/client")
@RestController
public class MyClient {
    private NioEventLoopGroup eventExecutors;
    private Bootstrap bootstrap;
    private SocketChannel socketChannel;

    public volatile String replyMsg;
    @PostConstruct
    public void initNettyClient() {
        eventExecutors = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 字符串编码器、解码器 必须放在最前面！！
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new MyClientHandler(MyClient.this));
                    }
                });

    }

    @PostMapping("/sendMessage")
    public Result runClient(@RequestParam String msg) throws InterruptedException {
        try {
            //通道为空 / 通道已经断开 → 重新建立连接
            if(socketChannel == null || !socketChannel.isActive()){
                ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
                socketChannel = (SocketChannel) channelFuture.channel();
            }
            //异步发送，不要阻塞Tomcat线程
            socketChannel.writeAndFlush(msg);
            return Result.successData(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("消息发送失败");
        }
    }


    // 新增接口：前端获取服务端返回的消息
    @GetMapping("/getReplyMsg")
    public Result getReply(){
        return Result.successData(replyMsg);
    }

    @PreDestroy
    public void closeNetty(){
        eventExecutors.shutdownGracefully();
    }
}
