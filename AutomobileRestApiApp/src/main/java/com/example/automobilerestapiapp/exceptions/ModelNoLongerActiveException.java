package com.example.automobilerestapiapp.exceptions;

public class ModelNoLongerActiveException extends RuntimeException{
  public ModelNoLongerActiveException(String message) {
    super(message);
  }
}
