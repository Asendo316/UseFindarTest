package com.usefindar.app.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JwtTokenFilterConfigurer
 */
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    // create a new filter and add to the builder.
    var tokenFilter = new JwtTokenFilter(jwtTokenProvider);
    builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
