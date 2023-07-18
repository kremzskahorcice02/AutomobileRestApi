package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.LogResponse;
import com.example.automobilerestapiapp.models.Log;
import com.example.automobilerestapiapp.services.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/logs")
public class LogController {
  private final LogService logService;

  @Autowired
  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping
  @Operation(summary = "Get all logs")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success")})
  public ResponseEntity<List<LogResponse>> getAllLogs() {
    logService.log(new Log().info().endpointRequest("/api/automobiles", "GET"));
    return ResponseEntity.ok().body(logService.getAllLogs());
  }

  @GetMapping("/{level}")
  @Operation(summary = "Get Log by log level",
  description = "Filters logs by log level - possible values are [INFO, ERROR, SUCCESS, INVALID] - all case insensitive")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success")})
  public ResponseEntity<List<LogResponse>> getLogsByLogLevel(@PathVariable("level") String level) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{level}", "GET"));
    return ResponseEntity.ok().body(logService.getLogsByLevel(level));
  }
}