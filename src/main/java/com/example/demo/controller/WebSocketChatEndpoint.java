//package com.example.demo.controller;
//
//
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@Component
//@ServerEndpoint("/ws/chat") // WebSocket访问地址，相当于接口
//public class WebSocketChatEndpoint {
//
//    // 存放所有在线浏览器客户端会话
//    private static final CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();
//
//    /**
//     * 浏览器连接成功触发
//     */
//    @OnOpen
//    public void onOpen(Session session){
//        sessionSet.add(session);
//        System.out.println("前端网页连接成功");
//    }
//
//    /**
//     * 浏览器发送消息过来
//     */
//    @OnMessage
//    public void onMessage(String msg,Session session){
//        System.out.println("浏览器发来消息："+msg);
//        //这里可以调用你的Netty客户端，把消息转发给Netty服务端
//    }
//
//    /**
//     * 连接关闭
//     */
//    @OnClose
//    public void onClose(Session session){
//        sessionSet.remove(session);
//    }
//
//    /**
//     * 发送消息给全部在线网页（重点！服务端主动推送）
//     */
//    public void sendAllMessage(String message) throws IOException {
//        for (Session session : sessionSet) {
//            if(session.isOpen()){
//                session.getBasicRemote().sendText(message);
//            }
//        }
//    }
//}