package com.example.async.api.task;

import lombok.Getter;

@Getter
public class RetryTaskResponse {
  private Long taskId;
  private String status;

  public RetryTaskResponse() {}

  public RetryTaskResponse(Long taskId, String status) {
    this.taskId = taskId;
    this.status = status;
  }
}
