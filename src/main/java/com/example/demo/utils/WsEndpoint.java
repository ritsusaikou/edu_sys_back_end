//package com.example.demo.utils;
//
//
//import com.example.demo.service.impl.CourseServiceImpl;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//
//
//@ServerEndpoint("/wf")
//@Component
//public class WsEndpoint {
//
//    private CourseServiceImpl getCourseServiceImpl(){
//        return SpringContextUtil.getBean(CourseServiceImpl.class);
//    }
//
//    @OnOpen
//    public void onOpen(Session session){
//        System.out.println("hello");
//    }
//
//    @OnClose
//    public void onClose(Session session,CloseReason closeReason){
//
//    }
//
//    @OnMessage
//    public void onMessage(Session session,String msg){
//        System.out.println(msg);
//        // 使用时动态获取Spring单例Service
//        CourseServiceImpl courseServiceImpl = getCourseServiceImpl();
//        System.out.println(courseServiceImpl.getPage(2, 10));
//    }
//
//    @OnError
//    public void onError(Session session,Throwable throwable){
//
//    }
//
//}
