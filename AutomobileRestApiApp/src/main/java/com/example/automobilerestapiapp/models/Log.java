package com.example.automobilerestapiapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Log {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String level;
  private LocalDateTime timestamp;
  private String message;
  public Log() {
    this.level = "INFO";
    this.timestamp = LocalDateTime.now();
  }
  public Log success() {
    this.level = "SUCCESS";
    return this;
  }

  public Log info() {
    this.level = "INFO";
    return this;
  }

  public Log error() {
    this.level = "ERROR";
    return this;
  }

  public Log notValid() {
    this.level = "INVALID";
    return this;
  }
  public Log setMessage(String message) {
    this.message = message;
    return this;
  }

  public Log endpointRequest(String path, String method) {
    this.message = method + " " + path;
    return this;
  }

  @Override
  public String toString() {
    return "[" + level + "] " +  timestamp + " " + message;
  }
}