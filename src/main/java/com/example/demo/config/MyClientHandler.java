//package com.example.demo.config;
//
//import com.example.demo.controller.MyClient;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//
//public class MyClientHandler extends ChannelInboundHandlerAdapter {
//
//    private final MyClient controller;
//
//    //构造方法注入controller
//    public MyClientHandler(MyClient controller){
//        this.controller = controller;
//    }
//
//    // 接收netty服务端返回消息
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        String receive = (String) msg;
//        System.out.println("收到服务端响应：" + receive);
//        // 将服务端消息存入控制器成员变量
//        controller.replyMsg = receive;
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }
//}