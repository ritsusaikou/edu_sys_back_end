//package com.example.demo.config;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//
//    // 存放所有客户端连接通道
//    public static final Map<String, Channel> ONLINE_CHANNEL = new ConcurrentHashMap<>();
//
//    // 客户端连接成功
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        Channel channel = ctx.channel();
//        ONLINE_CHANNEL.put(channel.id().asShortText(), channel);
//        System.out.println("客户端上线，通道id:" + channel.id().asShortText());
//    }
//
//    // 收到前端文本消息
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
//        String receiveMsg = msg.text();
//        System.out.println("收到浏览器消息：" + receiveMsg);
//
//        // 返回消息给当前连接的前端
//        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务端已收到：" + receiveMsg));
//    }
//
//    // 客户端断开
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) {
//        Channel channel = ctx.channel();
//        ONLINE_CHANNEL.remove(channel.id().asShortText());
//        System.out.println("客户端下线:" + channel.id().asShortText());
//    }
//
//    // 异常捕获
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }
//
//    // 群发消息示例
//    public static void sendAll(String text){
//        for (Channel channel : ONLINE_CHANNEL.values()) {
//            channel.writeAndFlush(new TextWebSocketFrame(text));
//        }
//    }
//}