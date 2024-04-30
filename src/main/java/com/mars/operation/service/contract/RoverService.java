package com.mars.operation.service.contract;

import com.mars.operation.model.CommandHistory;
import com.mars.operation.model.Rover;
import com.mars.operation.model.dto.RoverDetails;
import com.mars.operation.model.dto.RoverResponse;

import java.util.List;

public interface RoverService {
    Rover create(RoverDetails roverDetails);

    RoverResponse move(int id, String instructions);

    Rover linkToPlateau(int roverId, int plateauId);

    List<CommandHistory> history(int id);
}
