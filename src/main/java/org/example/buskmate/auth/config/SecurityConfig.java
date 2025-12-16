package org.example.buskmate.auth.config;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.auth.exception.AuthAccessDeniedHandler;
import org.example.buskmate.auth.exception.AuthEntryPoint;
import org.example.buskmate.auth.service.CustomOAuth2UserService;
import org.example.buskmate.auth.service.Oauth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthEntryPoint authEntryPoint;
    private final AuthAccessDeniedHandler authAccessDeniedHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final JwtDecoder jwtDecoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/oauth2/**",
                                "/api/auth/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)       // 401: 인증 없음/실패
                        .accessDeniedHandler(authAccessDeniedHandler)   // 403: 권한 부족
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oauth2LoginSuccessHandler)
                )
                .oauth2ResourceServer(resource -> resource
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(authAccessDeniedHandler)
                        .bearerTokenResolver(bearerTokenResolver())
                        .jwt(jwt -> jwt.decoder(jwtDecoder))
                );

        return http.build();
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return request -> {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if ("ACCESS_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
            return null;
        };
    }
}