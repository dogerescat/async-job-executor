package com.example.async.api.common.exception;

import com.example.async.api.domain.task.TaskStatus;

public class IllegalTaskTransitionException extends RuntimeException {

  private final TaskStatus from;
  private final TaskStatus to;

  public IllegalTaskTransitionException(TaskStatus from, TaskStatus to) {
    super("Illegal task transition: " + from + " -> " + to);
    this.from = from;
    this.to = to;
  }

  public TaskStatus getFrom() {
    return from;
  }

  public TaskStatus getTo() {
    return to;
  }
}
