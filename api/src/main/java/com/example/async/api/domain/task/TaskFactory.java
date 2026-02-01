package com.example.async.api.domain.task;

public class TaskFactory {
  public static Task createNew(String title, String description, String inputContext,
      FileType outputType) {
    return new Task(null, TaskStatus.PENDING, title, description, inputContext, outputType);
  }
}
