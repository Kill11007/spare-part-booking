package com.ford.sparepartbooking.exception;

import lombok.Getter;

public class InvalidLoginCredentialException extends Exception{

  @Getter
  private String message = "Please enter correct username/password.";

  public InvalidLoginCredentialException() {
    super(new Throwable("Entered username/password is not correct"));
  }

  public InvalidLoginCredentialException(String message) {
    super(message);
    this.message = message;
  }

  public InvalidLoginCredentialException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  public InvalidLoginCredentialException(Throwable cause) {
    super(cause);
  }
}
