package com.example.async.api.task;

import jakarta.validation.Valid;
import java.net.URI;
import com.example.async.api.application.task.CreateTaskCommand;
import com.example.async.api.application.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<CreateTaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
    var command = new CreateTaskCommand(request.getTitle(), request.getDescription(),
        request.getInputContext(), request.getOutputType());
    Long taskId = taskService.createTask(command);
    return ResponseEntity.created(URI.create("/tasks/" + taskId))
        .body(new CreateTaskResponse(taskId));
  }

  @PostMapping("/{id}/retry")
  public ResponseEntity<RetryTaskResponse> retry(@PathVariable("id") Long id) {
    var task = taskService.retryTask(id);
    return ResponseEntity.ok(new RetryTaskResponse(task.getId(), task.getStatus().name()));
  }
}
