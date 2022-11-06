package com.ford.sparepartbooking.service.impl;

import com.ford.sparepartbooking.dto.UserDTO;
import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.repository.UserRepository;
import com.ford.sparepartbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User addUser(UserDTO dto) {
    if (userRepository.findByName(dto.getName()).isPresent()){
      throw new RuntimeException("User Already Exists");
    }
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    return userRepository.save(new User(dto));
  }
}
