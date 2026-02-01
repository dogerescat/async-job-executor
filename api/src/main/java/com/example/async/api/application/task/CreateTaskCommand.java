package com.example.async.api.application.task;

public record CreateTaskCommand(String title, String description, String inputContext,
    String outputType) {
}
