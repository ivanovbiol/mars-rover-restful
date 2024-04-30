package com.mars.operation.repository;

import com.mars.operation.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    Direction findByName(String direction);
}
