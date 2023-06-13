package com.vz.ws.demo.stomp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author visy.wang
 * @description: WebSocket发送服务
 * @date 2023/5/9 20:21
 */
@Component
public class StompWsSender {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void send(String destination, String message){
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    public void send(String destination, String message, Map<String, Object> headers){
        simpMessagingTemplate.convertAndSend(destination, message, headers);
    }

    public void sendToUser(String user, String destination, Object payload){
        simpMessagingTemplate.convertAndSendToUser(user, destination, payload);
    }

    public void sendToUser(String user, String destination, Object payload, Map<String, Object> headers){
        simpMessagingTemplate.convertAndSendToUser(user, destination, payload, headers);
    }
}
