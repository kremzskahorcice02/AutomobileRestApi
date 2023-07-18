package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.LogResponse;
import com.example.automobilerestapiapp.models.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LogMapper {
  public static LogResponse toLogResponse(Log log) {
    return new LogResponse(
        log.getLevel(),
        log.getTimestamp(),
        log.getMessage()
    );
  }

  public static List<LogResponse> toLogResponseList(List<Log> logs) {
    List<LogResponse> logResponses = new ArrayList<>();
    logs.forEach(l -> logResponses.add(LogMapper.toLogResponse(l)));
    return logResponses;
  }
}
