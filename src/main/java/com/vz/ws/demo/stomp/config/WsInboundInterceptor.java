package com.vz.ws.demo.stomp.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author visy.wang
 * @description:
 * @date 2023/5/9 17:59
 */
@Component
public class WsInboundInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (Objects.nonNull(accessor)
                && StompCommand.CONNECT.equals(accessor.getCommand())) {
            System.out.println("login: "+ accessor.getLogin());
            System.out.println("passcode: "+ accessor.getPasscode());

            String Authentication = accessor.getFirstNativeHeader("Authentication");
            System.out.println("Authentication: "+ Authentication);
        }

        return message;
    }
}
