package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.LogResponse;
import com.example.automobilerestapiapp.mappers.LogMapper;
import com.example.automobilerestapiapp.models.Log;
import com.example.automobilerestapiapp.repositories.LogRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService{

  private final LogRepository logRepository;

  @Autowired
  public LogServiceImpl(LogRepository logRepository) {
    this.logRepository = logRepository;
  }
  @Override
  public void log(Log log) {
    logRepository.save(log);
    System.out.println(log.toString());
  }

  @Override
  public List<LogResponse> getLogsByLevel(String level) {
    List<Log> logs = logRepository.findAllByLevelContaining(level);
    return LogMapper.toLogResponseList(logs);
  }

  @Override
  public List<LogResponse> getAllLogs() {
    return LogMapper.toLogResponseList(logRepository.findAll());
  }
}