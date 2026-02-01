package com.example.async.api.domain.task;

import java.util.Optional;

/**
 * タスクリポジトリ
 */
public interface TaskRepository {
  Task save(Task task);

  Optional<Task> findById(Long id);

  /**
   * 状態が期待値のときだけ status を更新する（DB最終防衛線）
   * 
   * @return true: 更新成功 / false: 更新失敗（競合 or 状態不一致）
   */
  boolean updateStatusIfMatch(Long taskId, TaskStatus expected, TaskStatus next);
}
