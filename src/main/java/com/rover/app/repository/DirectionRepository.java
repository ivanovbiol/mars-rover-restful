package com.rover.app.repository;

import com.rover.app.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    Direction findByName(String direction);
}
