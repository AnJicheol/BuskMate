package org.example.buskmate.messenger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompJwtPrincipalInterceptor stompJwtPrincipalInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")

                // ⚠️ 토이 프로젝트 편의 설정: 운영 환경에서는 사용 금지
                // - 모든 Origin(*)에서 WebSocket 연결을 허용합니다.
                // - 운영에서는 프론트 도메인(CloudFront/S3)과 로컬 개발 도메인만 명시적으로 허용하세요.
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat/room");
        registry.setApplicationDestinationPrefixes("/chat/cmd");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompJwtPrincipalInterceptor);
    }
}
