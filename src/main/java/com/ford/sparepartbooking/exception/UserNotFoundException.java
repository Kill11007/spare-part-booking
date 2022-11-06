package com.ford.sparepartbooking.exception;

public class UserNotFoundException extends RuntimeException{
  private Long userId;

  public UserNotFoundException(Long userId) {
    super("User with id: " + userId + " not found.");
  }
}
