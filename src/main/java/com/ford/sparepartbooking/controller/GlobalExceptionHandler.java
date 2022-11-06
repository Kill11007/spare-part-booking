package com.ford.sparepartbooking.controller;

import com.ford.sparepartbooking.dto.ErrorDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public ErrorDTO handleException(RuntimeException exception) {
    return new ErrorDTO(exception.getMessage());
  }
}
