package com.example.async.api.infrastructure.persistence.taskexecution;

import com.example.async.api.domain.taskexecution.TaskExecution;

public class TaskExecutionMapper {

  public static TaskExecutionEntity toEntity(TaskExecution taskExecution) {
    return new TaskExecutionEntity(taskExecution);
  }

  public static TaskExecution toDomain(TaskExecutionEntity entity) {
    return new TaskExecution(entity.getId(), entity.getTaskId(), entity.getAttemptNo(),
        entity.getStatus());
  }
}
