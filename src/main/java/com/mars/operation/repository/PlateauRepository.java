package com.mars.operation.repository;

import com.mars.operation.model.Plateau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlateauRepository extends JpaRepository<Plateau, Integer> {
}
