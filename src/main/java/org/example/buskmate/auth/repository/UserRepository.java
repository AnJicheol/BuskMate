package org.example.buskmate.auth.repository;

import org.example.buskmate.auth.domain.OAuthProvider;
import org.example.buskmate.auth.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByProviderAndProviderUserId(OAuthProvider provider, String providerUserId);
}
