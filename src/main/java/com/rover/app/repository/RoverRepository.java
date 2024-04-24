package com.rover.app.repository;

import com.rover.app.model.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoverRepository extends JpaRepository<Rover, Integer> {
}
