package com.ford.sparepartbooking.exception;

public class OutOfStockException extends RuntimeException{
  private String itemName;

  public OutOfStockException(String itemName) {
    super("Item " + itemName + " is out of stock. Please come back later.");
  }

  public OutOfStockException() {super("This item is out of stock.");}
}

