package org.example.buskmate.messenger.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompJwtPrincipalInterceptor implements ChannelInterceptor {

}
