//package com.example.demo.config;
//
//import com.example.demo.utils.MyWebSocketHandler;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
//@Configuration
//@EnableWebSocket //开启Spring WebSocket支持
//public class SpringWebSocketConfig implements WebSocketConfigurer {
//
//    @Resource
//    private MyWebSocketHandler myWebSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myWebSocketHandler,"/ws/{sid}")
//                .setAllowedOrigins("*"); //允许全部跨域，postman调试必备
//    }
//}