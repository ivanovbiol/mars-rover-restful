package com.rover.app.controller;

import com.rover.app.model.dto.PlateauCoordinates;
import com.rover.app.service.contract.PlateauService;
import com.rover.app.service.contract.RoverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OperationsController {

    private final PlateauService plateauService;
    private final RoverService roverService;

    @PostMapping("/plateau")
    public ResponseEntity<String> createPlateau(PlateauCoordinates plateauCoordinates) {
        plateauService.create(plateauCoordinates);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/rover")
    public String health() {
        return "Healthy rover";
    }
}
