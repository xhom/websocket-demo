package com.vz.ws.demo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author visy.wang
 * @description: WebSocket 握手拦截器 建立握手时的事件
 * @date 2023/5/6 10:02
 */
@Component
public class WsHandShakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WsHandShakeInterceptor.class);

    /**
     * 握手前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("握手开始...");

        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        String authentication = servletRequest.getServletRequest().getHeader("Authentication");

        System.out.println("authentication: "+ authentication);

        //获取请求的参数（从URL）
        String queryString = request.getURI().getQuery();
        Map<String,String> queryParams = queryString2map(queryString);
        String token = queryParams.get("token");

        if(StringUtils.hasText(token)){
            //放入属性域
            attributes.put("token", token);
            logger.info("用户{}握手成功！", token);
            return true;
        }

        logger.info("用户登录已失效！");
        return false;
    }

    /**
     * 握手后
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("握手完成！");
    }

    private Map<String,String> queryString2map(String queryString){
        Map<String,String> mp = new HashMap<>();
        if(StringUtils.hasText(queryString)){
            String[] kvArray = queryString.split("&");
            for (String kv : kvArray) {
                String[] kvItem = kv.split("=");
                mp.put(kvItem[0], kvItem[1]);
            }
        }
        return mp;
    }
}
