package com.example.async.api.domain.task;

import com.example.async.api.common.exception.IllegalTaskTransitionException;
import lombok.Getter;

/**
 * タスクドメインモデル
 */
@Getter
public class Task {
  private final Long id;
  private final TaskStatus status;
  private final String title;
  private final String description;
  private final String inputContext;
  private final FileType outputType;

  public Task(Long id, TaskStatus status, String title, String description, String inputContext,
      FileType outputType) {
    this.id = id;
    this.status = status;
    this.title = title;
    this.description = description;
    this.inputContext = inputContext;
    this.outputType = outputType;
  }

  // ===============================
  // Domain rules (guards)
  // ===============================
  /** 実行開始できるか（PENDING のみ） */
  public boolean canStart() {
    return status == TaskStatus.PENDING;
  }

  /** 再実行できるか（FAILED のみ） */
  public boolean canRetry() {
    return status == TaskStatus.FAILED;
  }

  /** 完了に遷移できるか（RUNNING のみ） */
  public boolean canComplete() {
    return status == TaskStatus.RUNNING;
  }

  /** 失敗に遷移できるか（RUNNING のみ） */
  public boolean canFail() {
    return status == TaskStatus.RUNNING;
  }

  // ===============================
  // State transitions (pure)
  // ===============================

  public Task start() {
    if (!canStart()) {
      throw new IllegalTaskTransitionException(status, TaskStatus.RUNNING);
    }
    return withStatus(TaskStatus.RUNNING);
  }

  public Task complete() {
    if (!canComplete()) {
      throw new IllegalTaskTransitionException(status, TaskStatus.COMPLETED);
    }
    return withStatus(TaskStatus.COMPLETED);
  }

  public Task fail() {
    if (!canFail()) {
      throw new IllegalTaskTransitionException(status, TaskStatus.FAILED);
    }
    return withStatus(TaskStatus.FAILED);
  }

  public Task retry() {
    if (!canRetry()) {
      throw new IllegalTaskTransitionException(status, TaskStatus.RUNNING);
    }
    return withStatus(TaskStatus.RUNNING);
  }

  private Task withStatus(TaskStatus newStatus) {
    return new Task(this.id, newStatus, this.title, this.description, this.inputContext,
        this.outputType);
  }
}
