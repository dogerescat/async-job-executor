package com.example.async.api.common.exception;

public class TaskConflictException extends RuntimeException {
  public TaskConflictException(Long taskId, String message) {
    super("taskId=" + taskId + " " + message);
  }
}
