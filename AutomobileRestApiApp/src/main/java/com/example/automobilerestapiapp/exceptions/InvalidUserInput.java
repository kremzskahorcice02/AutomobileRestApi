package com.example.automobilerestapiapp.exceptions;

public class InvalidUserInput extends RuntimeException{

  public InvalidUserInput(String message) {
    super(message);
  }
}
