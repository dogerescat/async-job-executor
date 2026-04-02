package com.example.async.api.infrastructure.persistence.taskexecution;

import com.example.async.api.domain.taskexecution.TaskExecution;
import com.example.async.api.domain.taskexecution.TaskExecutionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TaskExecutionRepositoryImpl implements TaskExecutionRepository {
  private final TaskExecutionJpaRepository jpaRepository;

  public TaskExecutionRepositoryImpl(TaskExecutionJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public TaskExecution save(TaskExecution taskExecution) {
    TaskExecutionEntity saved = jpaRepository.save(TaskExecutionMapper.toEntity(taskExecution));
    return TaskExecutionMapper.toDomain(saved);
  }
}
