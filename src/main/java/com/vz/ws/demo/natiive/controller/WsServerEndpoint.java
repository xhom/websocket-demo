package com.vz.ws.demo.natiive.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author visy.wang
 * @description: 服务端消息处理
 * @date 2023/5/11 9:57
 */
@Component
@ServerEndpoint("/ws") //暴露ws的连接路径：ws://127.0.0.1:8080/ws
public class WsServerEndpoint {
    /*
     * 这些注解都是属于jdk自带的，并不是spring提供的，具体位置是在javax.websocket下，
     * 需要注意的是接收参数中的session，这是我们需要保存的，
     * 后面如果要对客户端发送消息的话使用session.getBasicRemote().sendText(XXX)
     */

    /**
     * 连接成功
     * @param session
     */
    @OnOpen //当websocket建立连接成功后会触发这个注解修饰的方法
    public void onOpen(Session session) {
        System.out.println("连接成功");
    }

    /**
     * 连接关闭
     * @param session
     */
    @OnClose //当websocket建立的连接断开后会触发这个注解修饰的方法
    public void onClose(Session session) {
        System.out.println("连接关闭");
    }

    @OnError //当websocket建立连接时出现异常会触发这个注解修饰的方法
    public void onError(){

    }

    /**
     * 接收到消息
     * @param text
     */
    @OnMessage //当客户端发送消息到服务端时，会触发这个注解修改的方法，它有一个 String 入参表明客户端传入的值
    public String OnMessage(String text) throws IOException {
        return "servet 发送：" + text;
    }
}
