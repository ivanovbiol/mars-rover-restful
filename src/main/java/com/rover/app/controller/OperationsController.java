package com.rover.app.controller;

import com.rover.app.model.Plateau;
import com.rover.app.model.Rover;
import com.rover.app.model.dto.PlateauCoordinates;
import com.rover.app.model.dto.RoverDetails;
import com.rover.app.service.contract.PlateauService;
import com.rover.app.service.contract.RoverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OperationsController {

    private final PlateauService plateauService;
    private final RoverService roverService;

    @PostMapping("/plateau")
    public ResponseEntity<Plateau> createPlateau(@RequestBody PlateauCoordinates plateauCoordinates) {
        return ResponseEntity.ok(plateauService.create(plateauCoordinates));
    }

    @PostMapping("/rover")
    public ResponseEntity<Rover> createRover(@RequestBody RoverDetails roverDetails) {
        return ResponseEntity.ok(roverService.create(roverDetails));
    }
}
