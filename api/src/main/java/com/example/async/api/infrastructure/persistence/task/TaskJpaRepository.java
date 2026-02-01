package com.example.async.api.infrastructure.persistence.task;

import com.example.async.api.domain.task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
  /**
   * 状態が期待値のときだけ新しい状態に更新する（ガード条件）
   *
   * @return 更新件数（1:成功 / 0:失敗＝競合 or 状態不一致）
   */
  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  @Query("""
          update TaskEntity t
             set t.status = :newStatus
           where t.id = :id
             and t.status = :expectedStatus
      """)
  int updateStatusIfMatch(@Param("id") Long id, @Param("expectedStatus") TaskStatus expectedStatus,
      @Param("newStatus") TaskStatus newStatus);
}
