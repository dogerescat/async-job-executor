package com.example.async.api.infrastructure.persistence.taskexecution;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.example.async.api.domain.taskexecution.TaskExecution;
import com.example.async.api.domain.taskexecution.TaskExecutionStatus;
import lombok.Getter;

@Getter
@Entity
@Table(name = "task_executions")
public class TaskExecutionEntity {
  public TaskExecutionEntity(TaskExecution taskExecution) {
    this.taskId = taskExecution.getTaskId();
    this.attemptNo = taskExecution.getAttemptNo();
    this.status = taskExecution.getStatus();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Long taskId;
  @Column(nullable = false)
  private Integer attemptNo;
  @Column
  private Integer workerId;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TaskExecutionStatus status;
  @Column
  private String errorCode;
  @Column
  private String errorMessage;
  @Column
  private String inputSnapshot;
  @Column
  private String resultSnapshot;
  @Column(nullable = false, updatable = false)
  private String createdAt;
}
