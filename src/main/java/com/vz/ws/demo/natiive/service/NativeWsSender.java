package com.vz.ws.demo.natiive.service;

import com.alibaba.fastjson.JSON;
import com.vz.ws.demo.natiive.config.SessionManager;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Objects;

/**
 * @author visy.wang
 * @description: WebSocket（原生）消息发送器
 * @date 2023/5/11 10:18
 */
@Component
public class NativeWsSender {

    public void send(Session session, Object message){
        if(Objects.nonNull(session) && session.isOpen()){
            //异步发送
            session.getAsyncRemote().sendText(JSON.toJSONString(message));
        }
    }

    public void send(String sessionId, Object message){
        send(SessionManager.get(sessionId), message);
    }

    public void sendToALl(Object message){
        String msg = JSON.toJSONString(message);
        SessionManager.forEach(session -> send(session, msg));
    }
}
