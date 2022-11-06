package com.ford.sparepartbooking.exception;

public class UserRoleNotFoundException extends RuntimeException{
  private String role;

  public UserRoleNotFoundException(String role) {
    super("User role" + role + " not found. Please enter a correct role (USER, ADMIN)");
    this.role = role;
  }


}
