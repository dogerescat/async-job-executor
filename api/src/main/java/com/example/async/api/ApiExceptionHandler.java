package com.example.async.api;

import java.util.Map;
import com.example.async.api.common.exception.IllegalTaskTransitionException;
import com.example.async.api.common.exception.TaskConflictException;
import com.example.async.api.common.exception.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(TaskNotFoundException e) {
    return ResponseEntity.status(404)
        .body(Map.of("error", "TASK_NOT_FOUND", "message", e.getMessage()));
  }

  @ExceptionHandler(IllegalTaskTransitionException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalTaskTransitionException e) {
    return ResponseEntity.status(409).body(Map.of("error", "ILLEGAL_TASK_TRANSITION", "message",
        e.getMessage(), "from", e.getFrom().name(), "to", e.getTo().name()));
  }

  @ExceptionHandler(TaskConflictException.class)
  public ResponseEntity<Map<String, Object>> handleConflict(TaskConflictException e) {
    return ResponseEntity.status(409)
        .body(Map.of("error", "TASK_CONFLICT", "message", e.getMessage()));
  }
}
