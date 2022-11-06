package com.ford.sparepartbooking.controller;

import com.ford.sparepartbooking.dto.JwtResponse;
import com.ford.sparepartbooking.dto.LoginRequest;
import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.exception.InvalidLoginCredentialException;
import com.ford.sparepartbooking.exception.NonRefreshableTokenException;
import com.ford.sparepartbooking.service.impl.JwtUserDetailsService;
import com.ford.sparepartbooking.util.JwtTokenUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailsService jwtUserDetailsService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> login(@RequestBody LoginRequest request)
      throws InvalidLoginCredentialException {
    authenticate(request.getName(), request.getPassword());
    final User userDetails = jwtUserDetailsService.loadUserByUsername(request.getName());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
    // From the HttpRequest get the claims
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
    if (claims == null) {
      throw new NonRefreshableTokenException("Token is not expired.");
    }
    Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
    String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    return ResponseEntity.ok(new JwtResponse(token));
  }

  public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
    return new HashMap<>(claims);
  }

  private void authenticate(String username, String password) throws InvalidLoginCredentialException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new InvalidLoginCredentialException("USER_DISABLED", e);
    } catch (Exception e) {
      throw new InvalidLoginCredentialException(e);
    }
  }
}
