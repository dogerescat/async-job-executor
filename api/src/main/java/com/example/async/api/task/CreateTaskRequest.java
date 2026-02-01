package com.example.async.api.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateTaskRequest {
  @NotBlank
  private String title;

  @NotBlank
  private String description;

  private String inputContext;

  private String outputType;



  public CreateTaskRequest() {}

  public CreateTaskRequest(String title, String description, String inputContext,
      String outputType) {
    this.title = title;
    this.description = description;
    this.inputContext = inputContext;
    this.outputType = outputType;
  }
}
