package com.example.async.api.task;

public class CreateTaskResponse {
  private Long taskId;

  public CreateTaskResponse() {}

  public CreateTaskResponse(Long taskId) {
    this.taskId = taskId;
  }

  public Long getTaskId() {
    return taskId;
  }
}
