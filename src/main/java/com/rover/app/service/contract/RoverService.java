package com.rover.app.service.contract;

import com.rover.app.model.Rover;
import com.rover.app.model.dto.MovementRequest;
import com.rover.app.model.dto.RoverDetails;
import com.rover.app.model.dto.RoverResponse;

public interface RoverService {
    Rover create(RoverDetails roverDetails);

    RoverResponse move(MovementRequest movementRequest);
}
