package com.ford.sparepartbooking.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ford.sparepartbooking.exception.UserRoleNotFoundException;
import java.util.Arrays;
import lombok.Getter;

public enum UserRole {
  USER("USER"),
  ADMIN("ADMIN");
  @Getter
  @JsonValue
  private final String role;

  UserRole(String role) {this.role=role;}

  public static UserRole of(String role) {
    return Arrays.stream(UserRole.values())
        .filter(userRole -> userRole.getRole().equalsIgnoreCase(role))
        .findAny()
        .orElseThrow(() -> new UserRoleNotFoundException(role));
  }
}
