package com.example.automobilerestapiapp.advice;

import com.example.automobilerestapiapp.dtos.ErrorResponse;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
import com.example.automobilerestapiapp.exceptions.InvalidUserInput;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Overrides default behavior of MethodArgumentNotValidException. Collect all errors raised during
   * validation a returns detailed explanation why each of the errors was thrown.
   *
   * @param ex      the exception to handle
   * @param headers the headers to be written to the response
   * @param status  the selected response status
   * @param request the current request
   * @return Response entity with list of error messages and status code 400
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<ErrorResponse> errorList = new ArrayList<>();
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    errors.forEach(error -> errorList.add(new ErrorResponse(error)));

    return ResponseEntity.status(400).body(errorList);
  }

  /**
   * Custom handler for MethodArgumentTypeMismatchException. Creates error message with explanation which
   * types caused that the exception was thrown.
   * @param ex exception type MethodArgumentTypeMismatchException
   * @return ResponseEntity object with error message
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String name = ex.getName();
    String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
    Object value = ex.getValue();
    String message = String.format("'%s' should be a valid type '%s' and '%s' isn't",
        name, type, value);

    return ResponseEntity.badRequest().body(new ErrorResponse(message));
  }

  /**
   * Handler for custom RecordNotFoundException exception. Creates error message with explanation that the
   * record the user searched for does not exist in a database.
   * @param exception exception that is handled
   * @return ResponseEntity object with error message and status code 404
   */
  @ExceptionHandler(value = RecordNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
    return new ResponseEntity<>(new ErrorResponse("Could not find record of id: " + exception.getId()), HttpStatus.NOT_FOUND);
  }

  /**
   * Handler for custom InvalidDateException exception. Creates error message with explanation that the
   * localDate value taken from user input is not valid and provides detailed message about the cause of the error.
   * @param exception exception that is handled
   * @return ResponseEntity object with error message and status code 400
   */
  @ExceptionHandler(value = {InvalidUserInput.class, InvalidDateException.class})
  public ResponseEntity<ErrorResponse> handleInvalidDateException(RuntimeException exception) {
    return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
  }
}