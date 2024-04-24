package com.rover.app.service.contract;

import com.rover.app.model.Rover;
import com.rover.app.model.dto.RoverDetails;

public interface RoverService {
    Rover create(RoverDetails roverDetails);
}
