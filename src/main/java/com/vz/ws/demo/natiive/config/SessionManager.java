package com.vz.ws.demo.natiive.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author visy.wang
 * @description: Session管理器
 * @date 2023/5/11 10:13
 */
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    //缓存已建立连接的会话
    private static final Map<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    public static void add(String key, Session session) {
        // 此处只允许一个用户的session链接。一个用户的多个连接，我们视为无效。
        SESSION_POOL.putIfAbsent(key, session);
    }

    public static Session get(String key){
        return SESSION_POOL.get(key);
    }

    public static Session remove(String key){
        return SESSION_POOL.remove(key);
    }

    public static void forEach(BiConsumer<? super String, ? super Session> consumer){
        SESSION_POOL.forEach(consumer);
    }

    public static void forEach(Consumer<? super Session> consumer){
        SESSION_POOL.values().forEach(consumer);
    }


}
