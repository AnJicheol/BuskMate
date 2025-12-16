package org.example.buskmate.messenger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Principal;


@Component
@RequiredArgsConstructor
public class StompJwtPrincipalInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null || accessor.getCommand() == null) {
            return message;
        }

        if (accessor.getCommand() == StompCommand.CONNECT) {

            Principal user = accessor.getUser();
            if (user instanceof Authentication auth && auth.isAuthenticated()) {
                // 이미 인증된 상태면 그냥 통과
                return message;
            }

        }

        return message;
    }
}
