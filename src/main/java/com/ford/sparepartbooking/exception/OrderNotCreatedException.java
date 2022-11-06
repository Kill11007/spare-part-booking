package com.ford.sparepartbooking.exception;

public class OrderNotCreatedException extends RuntimeException{
  String message;

  public OrderNotCreatedException(String message) {super(message);}
}
