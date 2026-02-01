package com.example.async.api.infrastructure.persistence.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import com.example.async.api.domain.task.FileType;
import com.example.async.api.domain.task.Task;
import com.example.async.api.domain.task.TaskStatus;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tasks")
public class TaskEntity {
  public TaskEntity(Task task) {
    this.status = task.getStatus();
    this.title = task.getTitle();
    this.description = task.getDescription();
    this.inputContext = task.getInputContext();
    this.outputType = task.getOutputType();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 128)
  private String title;

  @Column(nullable = false, length = 256)
  private String description;

  @Column(nullable = false, length = 256)
  private String inputContext;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType outputType;

  /**
   * タスクの要約状態（ユーザー視点）
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TaskStatus status;

  @Column(nullable = false)
  private Integer createdBy;

  /**
   * タスク作成日時
   */
  @Column(nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  /**
   * タスク更新日時
   */
  @Column(nullable = false)
  private OffsetDateTime updatedAt;
}
