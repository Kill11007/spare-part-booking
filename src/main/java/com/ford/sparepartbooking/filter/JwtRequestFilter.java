package com.ford.sparepartbooking.filter;

import com.ford.sparepartbooking.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService jwtUserDetailsService;

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String jwtToken = extractJwtFromRequest(request);

    String username = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    try {
      // Once we get the token validate it.
      if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

          UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

          // if token is valid configure Spring Security to manually set
          log.info("UserDetails authorities: {}", userDetails.getAuthorities().stream().map(
              GrantedAuthority::getAuthority).collect(
              Collectors.toList()));
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken
              .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // After setting the Authentication in the context, we specify
          // that the current user is authenticated. So it passes the
          // Spring Security Configurations successfully.
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      } else {
        logger.warn("JWT Token does not begin with Bearer String");
      }
    } catch (IllegalArgumentException e) {
      request.setAttribute("exception", e);
    } catch (ExpiredJwtException e) {
      String isRefreshToken = request.getHeader("isRefreshToken");
      String requestURL = request.getRequestURL().toString();
      // allow for Refresh Token creation if following conditions are true.
      if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains(
          "refresh-token")) {
        allowForRefreshToken(e, request);
      } else {
        request.setAttribute("exception", e);
      }
      log.info("JWT Token has expired");
    }
    filterChain.doFilter(request, response);
  }

  private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
    // create a UsernamePasswordAuthenticationToken with null values.
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        null, null, null);
    // After setting the Authentication in the context, we specify
    // that the current user is authenticated. So it passes the
    // Spring Security Configurations successfully.
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    // Set the claims so that in controller we will be using it to create
    // new JWT
    request.setAttribute("claims", ex.getClaims());
  }

  private String extractJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
