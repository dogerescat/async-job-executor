package com.example.async.api.application.port;

public interface TaskExecutionMessagePublisher {
  void publish(Long taskId);
}
