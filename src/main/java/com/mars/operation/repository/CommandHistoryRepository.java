package com.mars.operation.repository;

import com.mars.operation.model.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Integer> {
}
