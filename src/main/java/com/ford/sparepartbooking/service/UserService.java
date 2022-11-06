package com.ford.sparepartbooking.service;

import com.ford.sparepartbooking.dto.UserDTO;
import com.ford.sparepartbooking.entity.User;

public interface UserService {
  User addUser(UserDTO dto);
}
