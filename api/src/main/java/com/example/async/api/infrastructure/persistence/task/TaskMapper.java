package com.example.async.api.infrastructure.persistence.task;


import com.example.async.api.domain.task.Task;

public class TaskMapper {
  private TaskMapper() {}

  public static Task toDomain(TaskEntity e) {
    return new Task(e.getId(), e.getStatus(), e.getTitle(), e.getDescription(), e.getInputContext(),
        e.getOutputType());
  }

  public static TaskEntity toEntity(Task d) {
    return new TaskEntity(d);
  }
}
