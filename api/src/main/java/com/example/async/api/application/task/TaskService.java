package com.example.async.api.application.task;

import com.example.async.api.application.port.TaskExecutionMessagePublisher;
import com.example.async.api.common.exception.TaskConflictException;
import com.example.async.api.common.exception.TaskNotFoundException;
import com.example.async.api.domain.task.FileType;
import com.example.async.api.domain.task.Task;
import com.example.async.api.domain.task.TaskFactory;
import com.example.async.api.domain.task.TaskRepository;
import com.example.async.api.domain.task.TaskStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
  private final TaskRepository taskRepository;

  private final TaskExecutionMessagePublisher taskExecutionMessagePublisher;

  public TaskService(TaskRepository taskRepository,
      TaskExecutionMessagePublisher taskExecutionMessagePublisher) {
    this.taskRepository = taskRepository;
    this.taskExecutionMessagePublisher = taskExecutionMessagePublisher;
  }

  @Transactional
  public Long createTask(CreateTaskCommand command) {
    var task = TaskFactory.createNew(command.title(), command.description(), command.inputContext(),
        FileType.valueOf(command.outputType()));
    Task saved = taskRepository.save(task);

    taskExecutionMessagePublisher.publish(saved.getId());
    return saved.getId();
  }

  @Transactional
  public Task retryTask(Long taskId) {
    Task task =
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

    // domain で遷移ルールを強制
    task.retry();

    boolean ok = taskRepository.updateStatusIfMatch(taskId, TaskStatus.FAILED, TaskStatus.RUNNING);
    if (!ok) {
      throw new TaskConflictException(taskId,
          "Failed to update task status due to concurrent modification or state mismatch.");
    }
    return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
  }
}
