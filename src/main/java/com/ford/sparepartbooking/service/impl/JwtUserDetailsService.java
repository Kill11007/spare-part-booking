package com.ford.sparepartbooking.service.impl;

import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByName(username)
          .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
