package com.vz.ws.demo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author visy.wang
 * @description: WebSocket会话管理器
 * @date 2023/5/6 9:51
 */
public class WsSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(WsSessionManager.class);
    //缓存已建立连接的会话
    private static final Map<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    public static void add(String key, WebSocketSession session){
        SESSION_POOL.put(key, session);
    }

    public static WebSocketSession get(String key){
        return SESSION_POOL.get(key);
    }

    public static WebSocketSession remove(String key){
        return SESSION_POOL.remove(key);
    }

    public static void removeAndClose(String key){
        WebSocketSession session = remove(key);
        if(Objects.isNull(session)){
            return;
        }
        try{
            session.close();
        }catch (IOException e){
            logger.info("会话{}关闭异常：{}", key, e.getMessage(), e);
        }
    }
}
