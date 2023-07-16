package com.example.automobilerestapiapp.exceptions;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message) {
      super(message);
  }
}
