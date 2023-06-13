package com.vz.ws.demo.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author visy.wang
 * @description: WebSocket(Spring)配置
 * @date 2023/5/5 18:39
 */
//@Configuration
//@EnableWebSocket
public class WsSpringConfig implements WebSocketConfigurer {
    @Autowired
    private WsMessageHandler wsMessageHandler;
    @Autowired
    private WsHandShakeInterceptor handShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(wsMessageHandler, "/ws")
                .addInterceptors(handShakeInterceptor) //new HttpSessionHandshakeInterceptor()
                .setAllowedOrigins("*");
    }
    //https://www.jianshu.com/p/57fbfadacfeb
}
