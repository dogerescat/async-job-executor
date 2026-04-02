package com.example.async.api.infrastructure.persistence.taskexecution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskExecutionJpaRepository extends JpaRepository<TaskExecutionEntity, Long> {

}
