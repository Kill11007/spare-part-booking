package com.ford.sparepartbooking.dto;

import com.ford.sparepartbooking.entity.User;
import com.ford.sparepartbooking.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String name;
  private String password;
  private Long id;
  private UserRole userRole;

  public UserDTO(User user) {
    setName(user.getUsername());
    setId(user.getId());
    setUserRole(userRole);
  }
}
