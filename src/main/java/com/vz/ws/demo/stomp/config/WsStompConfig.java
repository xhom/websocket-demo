package com.vz.ws.demo.stomp.config;

import com.vz.ws.demo.spring.WsHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author visy.wang
 * @description: WebSocket(Stomp)配置
 * @date 2023/5/6 12:24
 */
@Configuration
@EnableWebSocketMessageBroker
public class WsStompConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private WsHandShakeInterceptor handShakeInterceptor;
    @Autowired
    private WsInboundInterceptor wsInboundInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端连接地址
        registry.addEndpoint("/ws") //设置连接节点，前端请求的建立连接的地址就是 http://ip:端口/ws
                //.addInterceptors(handShakeInterceptor) //设置握手拦截器
                .setAllowedOriginPatterns("*") //配置跨域
                .withSockJS(); //开启sockJS支持，可以兼容不支持stomp的浏览器
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 消息代理,这里配置自带的消息代理，也可以配置其它的消息代理
        //订阅Broker名称:topic 代表发布广播，即群发
        //queue 代表点对点，即发指定用户
        registry.enableSimpleBroker("/topic", "/queue");

        // 客户端向服务端发送消息需有的前缀,需要什么样的前缀在这里配置,但是不建议使用，
        // 这里跟下面首次订阅返回会有点冲突，如果不需要首次订阅返回消息，也可以加上消息前缀
        // registry.setApplicationDestinationPrefixes("/");

        // send命令时需要带上/app前缀，不设置默认是/
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        // 若使用了setApplicationDestinationPrefixes方法，
        // 则作用主要体现在@SubscribeMapping和@MessageMapping上。
        // 如控制层配置@MessageMapping("/sendToServer")，则客户端发送的地址是 /app/sendToServer
        registry.setApplicationDestinationPrefixes("/app");

        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        // 配置单发消息的前缀 /user，前端订阅一对一通信的地址需要加上"/user"前缀
        registry.setUserDestinationPrefix("/user");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(wsInboundInterceptor);
    }
}
