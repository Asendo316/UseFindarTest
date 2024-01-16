package com.usefindar.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usefindar.app.models.response.system.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * {JwtTokenFilter} We should use OncePerRequestFilter since we are doing a
 * database call, there is no point in doing this more than once
 */
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
  private JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    var token = jwtTokenProvider.resolveToken(request);
    try {
      if (token != null && !jwtTokenProvider.isTokenBlacklisted(token) && jwtTokenProvider.validateToken(token)) {
        var auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else {
        throw new Exception("FORBIDDEN");
      }
    } catch (Exception e) {
      // this is very important, since it guarantees the user is not authenticated at all
      SecurityContextHolder.clearContext();

      ApiResponse<?> res = new ApiResponse<>(HttpStatus.FORBIDDEN);
      res.setError("FORBIDDEN");
      //set the response headers
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType("application/json");

      var mapper = new ObjectMapper();
      mapper.findAndRegisterModules();
      var out = response.getWriter();
      out.print(mapper.writeValueAsString(res ));
      out.flush();
      return;
    }

    filterChain.doFilter(request, response);
  }
}