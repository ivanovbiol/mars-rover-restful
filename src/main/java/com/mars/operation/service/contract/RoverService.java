package com.mars.operation.service.contract;

import com.mars.operation.model.Rover;
import com.mars.operation.model.dto.RoverDetails;
import com.mars.operation.model.dto.RoverResponse;

public interface RoverService {
    Rover create(RoverDetails roverDetails);

    RoverResponse move(int id, String instructions);

    Rover linkToPlateau(int roverId, int plateauId);
}
