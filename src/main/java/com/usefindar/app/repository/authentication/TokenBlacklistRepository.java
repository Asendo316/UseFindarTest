package com.usefindar.app.repository.authentication;

import com.usefindar.app.entities.authentication.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {
  boolean existsByToken(String token);
}
