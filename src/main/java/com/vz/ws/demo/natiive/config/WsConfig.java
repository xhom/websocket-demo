package com.vz.ws.demo.natiive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author visy.wang
 * @description: WebSocket(原生)配置
 * @date 2023/5/11 9:55
 */
//@Configuration
//@EnableWebSocket
public class WsConfig {
    @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }
}
