package com.ford.sparepartbooking.exception;

public class NonRefreshableTokenException extends RuntimeException{

  public NonRefreshableTokenException(String message) {
    super(message);
  }
}
