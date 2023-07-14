package com.example.automobilerestapiapp.exceptions;
public class RecordNotFoundException extends RuntimeException{

  private Long id;

  public RecordNotFoundException(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}