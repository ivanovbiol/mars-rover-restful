package com.rover.app.repository;

import com.rover.app.model.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Integer> {
}
