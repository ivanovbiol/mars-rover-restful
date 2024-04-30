package com.mars.operation.controller;

import com.mars.operation.model.Plateau;
import com.mars.operation.model.Rover;
import com.mars.operation.model.dto.PlateauCoordinates;
import com.mars.operation.model.dto.RoverDetails;
import com.mars.operation.model.dto.RoverResponse;
import com.mars.operation.service.contract.PlateauService;
import com.mars.operation.service.contract.RoverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@AllArgsConstructor
public class OperationsController {

    private final PlateauService plateauService;
    private final RoverService roverService;

    @PostMapping("/plateau")
    public ResponseEntity<Plateau> createPlateau(@RequestBody @Valid PlateauCoordinates plateauCoordinates) {
        return ResponseEntity.ok(plateauService.create(plateauCoordinates));
    }

    @PostMapping("/rover")
    public ResponseEntity<Rover> createRover(@RequestBody @Valid RoverDetails roverDetails) {
        return ResponseEntity.ok(roverService.create(roverDetails));
    }

    @PatchMapping("/rover/{roverId}/plateau/{plateauId}")
    public ResponseEntity<Rover> linkRoverToPlateau(@PathVariable @Positive(message = "Rover ID must be positive") int roverId,
                                                    @PathVariable @Positive(message = "Plateau ID must be positive") int plateauId) {
        return ResponseEntity.ok(roverService.linkToPlateau(roverId, plateauId));
    }

    @PostMapping("/rover/{id}/move")
    public ResponseEntity<RoverResponse> moveRover(@PathVariable @Positive int id,
                                                   @RequestParam @NotBlank(message = "Instructions can't be blank") String instructions) {
        return ResponseEntity.ok(roverService.move(id, instructions));
    }

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
