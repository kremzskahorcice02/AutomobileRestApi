package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.LogResponse;
import com.example.automobilerestapiapp.models.Log;
import java.util.List;

public interface LogService {

  /**
   * Method saves new Log into the database
   * @param log log object to be saved
   */
  void log(Log log);

  /**
   * Searches for a Logs with given log level
   * @param level log level to filter with
   * @return list of log dtos
   */
  List<LogResponse> getLogsByLevel(String level);

  /**
   * Fetches all Logs from the databse
   * @return list of log dtos
   */
  List<LogResponse> getAllLogs();
}