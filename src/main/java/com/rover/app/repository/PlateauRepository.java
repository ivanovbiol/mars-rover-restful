package com.rover.app.repository;

import com.rover.app.model.Plateau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlateauRepository extends JpaRepository<Plateau, Integer> {
}
