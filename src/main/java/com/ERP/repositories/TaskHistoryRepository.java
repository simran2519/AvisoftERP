package com.ERP.repositories;

import com.ERP.entities.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
//    List<TaskHistory> findAllByProjectId(long projectId);
//    List<TaskHistory> deleteByProjectId(long projectId);
}
