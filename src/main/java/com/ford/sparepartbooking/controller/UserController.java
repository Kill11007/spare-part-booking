package com.ford.sparepartbooking.controller;

import com.ford.sparepartbooking.dto.UserDTO;
import com.ford.sparepartbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Void> signUp(@RequestBody UserDTO dto) {
    userService.addUser(dto);
    return ResponseEntity.ok().build();
  }
}
