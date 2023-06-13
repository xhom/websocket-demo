package com.vz.ws.demo.stomp.controller;

import com.vz.ws.demo.common.Result;
import com.vz.ws.demo.stomp.service.StompWsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author visy.wang
 * @description: WebSocket控制器，连接入口
 * @date 2023/5/6 12:33
 */
@RestController
public class WsController {
    @Autowired
    private StompWsSender stompWsSender;

    /*
     * 注解说明：
     * @MessageMapping：功能与RequestMapping注解类似，send指令发送信息时添加此注解
     * @SendTo/@SendToUser：将信息输出到该主题。客户端订阅同样的主题后就会收到信息
     * 在只有指定@MessageMapping时@MessageMapping 相当于 “/topic” + @SendTo
     *
     */


    // ------------------------客户端发起订阅---------------------------

    //通用群发消息的订阅
    @SubscribeMapping("/topic/all")
    public Result subscribeAll() {
        return Result.success("群发消息订阅成功");
    }

    //指定一部分人可以收到的群发消息的订阅
    @SubscribeMapping("/topic/info")
    public Result subscribeClassify() {
        return Result.success("分类消息订阅成功");
    }

    //一对一消息的订阅
    @SubscribeMapping("/user/queue")
    public Result subscribeUser() {
        return Result.success("一对一消息订阅成功");
    }

    // ------------------------接收客户端的消息---------------------------

    //服务端接收客户端发送来的消息
    @MessageMapping("/sendToServer") //前端发送地址：/app/sendToServer
    public Result sendToServer(String message) {
        System.out.println("接收消息（sendToServer）：" + message);
        return Result.success("服务端接收到你发的："+message);
    }

    //服务端接收客户端发送来的消息并广播出去
    @MessageMapping("topic/sendToTopic")
    @SendTo("/topic/all") //将消息发送到订阅了“/app/topic/all”的所有客户端
    public Result sendToTopic(String message) {
        System.out.println("接收消息（sendToTopic）：" + message);
        return Result.success(message);
    }

    @MessageMapping("/sendToUser")
    public void sendToUser(String message) {
        System.out.println("接收消息（sendToUser）：" + message);
        stompWsSender.sendToUser("ZhangSan", "/topic/all", message);
    }

    // ------------------------主动向客户端发送消息---------------------------

    @GetMapping("/sendMsgToUser")
    public Result sendMsgByUser(String name, String msg) {
        // /user/{name}/hello
        stompWsSender.sendToUser(name, "/hello", msg);
        return Result.success();
    }

    @GetMapping("/sendMsgToAll")
    public Result sendMsgByAll(int classId, String msg) {
        // /topic/info/{classId}
        stompWsSender.send("/topic/info/"+classId, msg);
        return Result.success();
    }

    @GetMapping("/sendMsgToHello")
    public Result sendMsgToHello(String msg) {
        System.out.println("sendMsgToHello: "+ msg);
        stompWsSender.send("/app/topic/all", msg);
        return Result.success();
    }
}
