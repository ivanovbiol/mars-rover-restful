package com.mars.operation.repository;

import com.mars.operation.model.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Integer> {
    @Query("SELECT ch FROM CommandHistory ch WHERE ch.rover.id = :roverId")
    List<CommandHistory> findByRoverId(@Param("roverId") int roverId);
}
