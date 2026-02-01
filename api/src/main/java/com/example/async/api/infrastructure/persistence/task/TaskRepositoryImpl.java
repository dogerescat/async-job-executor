package com.example.async.api.infrastructure.persistence.task;

import java.util.Optional;
import com.example.async.api.domain.task.Task;
import com.example.async.api.domain.task.TaskRepository;
import com.example.async.api.domain.task.TaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

  private final TaskJpaRepository jpaRepository;

  public TaskRepositoryImpl(TaskJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Task save(Task task) {
    TaskEntity saved = jpaRepository.save(TaskMapper.toEntity(task));
    return TaskMapper.toDomain(saved);
  }

  @Override
  public Optional<Task> findById(Long id) {
    return jpaRepository.findById(id).map(TaskMapper::toDomain);
  }

  @Override
  public boolean updateStatusIfMatch(Long taskId, TaskStatus expected, TaskStatus next) {
    int updated = jpaRepository.updateStatusIfMatch(taskId, expected, next);
    return updated == 1;
  }
}
