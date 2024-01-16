package com.usefindar.app.security;

import com.usefindar.app.repository.authentication.TokenBlacklistRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/** JwtTokenProvider */
@Component
public class JwtTokenProvider {

  @Value("${api.security.jwt.token.secret-key}")
  private String secretKey;
  @Value("${api.security.jwt.token.expire-length}")
  private long validityInMilliseconds;

  private final UserDetailsService myUserDetails;
  private final TokenBlacklistRepository tokenBlacklistRepository;

  @Autowired
  public JwtTokenProvider(UserDetailsService myUserDetails, TokenBlacklistRepository tokenBlacklistRepository) {
    this.myUserDetails = myUserDetails;
    this.tokenBlacklistRepository = tokenBlacklistRepository;
  }

  @PostConstruct
  public void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  protected String generateToken(String subject, Long validityPeriod) {
    var claims = Jwts.claims().setSubject(subject);
    var now = new Date();
    var validity = new Date(now.getTime() + validityPeriod);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

  public String createToken(String userId) {
    return generateToken(userId, validityInMilliseconds);
  }

  public String resolveToken(HttpServletRequest request) {
    var bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    var userDetails = myUserDetails.loadUserByUsername(getUserId(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUserId(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isTokenBlacklisted(String token) {
    return tokenBlacklistRepository.existsByToken(token);
  }

  private Claims getAllClaims(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    return getAllClaims(token).getExpiration().before(new Date());
  }
}
