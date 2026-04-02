package com.example.async.api.domain.taskexecution;

import lombok.Getter;

@Getter
public class TaskExecution {
  private final Long id;
  private final Long taskId;
  private final Integer attemptNo;
  private final TaskExecutionStatus status;

  public TaskExecution(Long id, Long taskId, Integer attemptNo, TaskExecutionStatus status) {
    this.id = id;
    this.taskId = taskId;
    this.attemptNo = attemptNo;
    this.status = status;
  }


}
