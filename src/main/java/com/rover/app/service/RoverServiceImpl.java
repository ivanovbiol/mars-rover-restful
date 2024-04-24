package com.rover.app.service;

import com.rover.app.model.Rover;
import com.rover.app.model.dto.RoverDetails;
import com.rover.app.repository.DirectionRepository;
import com.rover.app.repository.PlateauRepository;
import com.rover.app.repository.RoverRepository;
import com.rover.app.service.contract.RoverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RoverServiceImpl implements RoverService {

    private final RoverRepository roverRepository;
    private final PlateauRepository plateauRepository;
    private final DirectionRepository directionRepository;

    @Override
    public Rover create(RoverDetails roverDetails) {
        Rover rover = roverRepository.save(Rover.builder()
                .x(roverDetails.getX())
                .y(roverDetails.getY())
                .plateau(plateauRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Create a Plateau first")))
                .direction(directionRepository.findByName(roverDetails.getDirection()))
                .build());
        log.info("Rover created");
        return rover;
    }
}
