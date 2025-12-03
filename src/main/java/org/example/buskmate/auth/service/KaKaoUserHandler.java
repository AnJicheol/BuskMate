package org.example.buskmate.auth.service;

import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.example.buskmate.auth.domain.OAuthProvider;
import org.example.buskmate.auth.domain.Users;
import org.example.buskmate.auth.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KaKaoUserHandler implements ProviderUserHandler {

    private final UserRepository usersRepository;

    @Override
    public boolean supports(String registrationId) {
        return OAuthProvider.KAKAO.name().equalsIgnoreCase(registrationId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public OAuth2User handle(OAuth2UserRequest userRequest,
                             Map<String, Object> attributes) {


        Long kakaoId = ((Number) attributes.get("id")).longValue();
        String providerUserId = String.valueOf(kakaoId);

        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.getOrDefault("kakao_account", Map.of());
        Map<String, Object> profile =
                (Map<String, Object>) kakaoAccount.getOrDefault("profile", Map.of());

        String email = (String) kakaoAccount.get("email");
        String name = (String) profile.get("nickname");

        if (name == null) {
            throw new OAuth2AuthenticationException("카카오 프로필 닉네임이 없습니다.");
        }
        if (name == email) {
            throw new OAuth2AuthenticationException("카카오 프로필 닉네임이 없습니다.");
        }

        //  최초 로그인 -> 회원 가입
        Users user = usersRepository
                .findByProviderAndProviderUserId(OAuthProvider.KAKAO, providerUserId)
                .orElseGet(() -> {
                    String newUserId = UlidCreator.getUlid().toString();
                    Users newUser = new Users(
                            newUserId,
                            name,
                            email,
                            OAuthProvider.KAKAO,
                            providerUserId
                    );
                    return usersRepository.save(newUser);
                });

        Collection<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));


        return new UsersPrincipal(
                user.getUserId(),
                user.getName(),
                authorities,
                attributes
        );
    }
}
