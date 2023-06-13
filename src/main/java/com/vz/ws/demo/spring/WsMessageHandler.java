package com.vz.ws.demo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author visy.wang
 * @description: WebSocket消息处理器
 * @date 2023/5/6 9:32
 */
@Component
public class WsMessageHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(WsMessageHandler.class);

    /**
     * socket 连接建立成功后触发
     * @param session 会话
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = getToken(session);
        if(Objects.nonNull(token)){
            WsSessionManager.add(token, session);
        }else{
            logger.error("用户登录已经失效");
        }
    }

    /**
     * 客户端发送消息时触发
     * @param session 会话
     * @param message 消息
     * @throws Exception 异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String token = getToken(session);
        String payload = message.getPayload();

        logger.info("服务端接收到{}发送的消息：{}", token, payload);

        String msg = String.format("服务端回复给%s的消息：%s [%s]", token, payload, LocalDateTime.now());
        TextMessage sendMsg = new TextMessage(msg);
        session.sendMessage(sendMsg);
    }

    /**
     * 连接断开后触发
     * @param session 会话
     * @param status 关闭状态
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String token = getToken(session);
        if(Objects.nonNull(token)){
            //从缓存中移除会话
            WsSessionManager.remove(token);
        }
    }

    private static String getToken(WebSocketSession session){
        Object token = session.getAttributes().get("token");
        return Objects.nonNull(token) ? token.toString() : null;
    }
}
