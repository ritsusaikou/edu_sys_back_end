package com.example.demo.utils;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    // 存放所有客户端连接 sid -> session
    public static final Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();



    /**
     * 客户端建立连接成功
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = Objects.requireNonNull(session.getUri()).getPath(); // 示例：/ws/pknqmqttai8
        String sid = path.substring(path.lastIndexOf('/') + 1);
        SESSION_MAP.put(sid, session);
        System.out.println("客户端连接成功");
    }

    public String getSidBySession(WebSocketSession targetSession){
        for (Map.Entry<String, WebSocketSession> entry : SESSION_MAP.entrySet()) {
            if (entry.getValue() == targetSession){
                return entry.getKey();
            }
        }
        return null; //找不到
    }

    /**
     * 接收客户端发来的文本消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        if(msg.startsWith("@")){
            String toSid = msg.substring(1,msg.indexOf(':'));
            String trueMsg = msg.substring(msg.indexOf(':'));
            WebSocketSession toSession = SESSION_MAP.get(toSid);
            toSession.sendMessage(new TextMessage(getSidBySession(session)+"向你发送"+trueMsg));
        }


//        System.out.println("收到客户端消息：" + message.getPayload());
//        // 返回消息给客户端
//        session.sendMessage(new TextMessage("服务端已收到："+message.getPayload()));
    }

    /**
     * 连接关闭
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sid = session.getAttributes().get("sid").toString();
//        SESSION_MAP.remove(sid);
        System.out.println("客户端断开连接 sid = " + sid);
    }

    /**
     * 传输异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }
//    @Scheduled(cron ="0/5 * * * * ?" )
    public void sendAll() throws IOException {
        Map<String, WebSocketSession> sessionMap = SESSION_MAP;
        if (!CollectionUtil.isEmpty(sessionMap)){
            for (String s : sessionMap.keySet()) {
                WebSocketSession webSocketSession = sessionMap.get(s);
                String msg = "广播一下时间" + LocalDateTime.now();
                webSocketSession.sendMessage(new TextMessage(msg));
            }
        }
    }
}