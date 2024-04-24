package com.rover.app.service;

import com.rover.app.model.CommandHistory;
import com.rover.app.model.Plateau;
import com.rover.app.model.Rover;
import com.rover.app.model.dto.MovementRequest;
import com.rover.app.model.dto.RoverDetails;
import com.rover.app.model.dto.RoverResponse;
import com.rover.app.repository.CommandHistoryRepository;
import com.rover.app.repository.DirectionRepository;
import com.rover.app.repository.PlateauRepository;
import com.rover.app.repository.RoverRepository;
import com.rover.app.service.contract.RoverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RoverServiceImpl implements RoverService {

    private final RoverRepository roverRepository;
    private final PlateauRepository plateauRepository;
    private final DirectionRepository directionRepository;
    private final CommandHistoryRepository commandHistoryRepository;

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

    @Override
    @Transactional
    public RoverResponse move(MovementRequest movementRequest) {

        // Find the rover from the repository
        Rover rover = roverRepository.findById(movementRequest.getRoverId())
                .orElseThrow(() -> new IllegalArgumentException("No such Rover"));

        // Retrieve the plateau details
        Plateau plateau = plateauRepository.findById(rover.getPlateau().getId())
                .orElseThrow(() -> new IllegalStateException("Rover is not on any plateau"));

        // Move the rover based on the instructions
        moveRover(rover, movementRequest.getInstructions(), plateau);

        // Create and return the response DTO
        return createRoverResponse(roverRepository.save(rover));
    }

    private RoverResponse createRoverResponse(Rover rover) {
        return RoverResponse.builder()
                .id(rover.getId())
                .currentX(rover.getX())
                .currentY(rover.getY())
                .direction(rover.getDirection().getName()).build();
    }

    private void moveRover(Rover rover, String instructions, Plateau plateau) {
        int x = rover.getX();
        int y = rover.getY();
        String direction = rover.getDirection().getName();

        for (char instruction : instructions.toCharArray()) {
            //Create Command history
            CommandHistory commandHistory = commandHistoryRepository.save(CommandHistory.builder()
                    .rover(rover)
                    .direction(directionRepository.findByName(direction))
                    .instruction(Character.toString(instruction))
                    .currentRoverX(x)
                    .currentRoverY(y)
                    .executed(false).build());

            if (instruction == 'L') {
                direction = turnLeft(direction);

                //Finish and save the command history
                commandHistoryRepository.save(commandHistory.toBuilder().direction(directionRepository.findByName(direction)).executed(true).build());
            } else if (instruction == 'R') {
                direction = turnRight(direction);

                //Finish and save the command history
                commandHistoryRepository.save(commandHistory.toBuilder().direction(directionRepository.findByName(direction)).executed(true).build());
            } else if (instruction == 'M') {
                int nextX = x;
                int nextY = y;
                if (direction.equals("N")) {
                    nextY++;
                } else if (direction.equals("E")) {
                    nextX++;
                } else if (direction.equals("S")) {
                    nextY--;
                } else if (direction.equals("W")) {
                    nextX--;
                }
                if (isValidPosition(nextX, nextY, plateau.getWidth(), plateau.getHeight())) {
                    x = nextX;
                    y = nextY;

                    //Finish and save the command history
                    commandHistoryRepository.save(commandHistory.toBuilder().currentRoverX(x).currentRoverY(y).executed(true).build());
                }
            } else {
                throw new IllegalArgumentException("Invalid instruction: " + instruction);
            }


        }

        // Update the rover's position after executing all instructions
        rover.setX(x);
        rover.setY(y);
        rover.setDirection(directionRepository.findByName(direction));
    }

    private String turnLeft(String direction) {
        switch (direction) {
            case "N":
                return "W";
            case "E":
                return "N";
            case "S":
                return "E";
            case "W":
                return "S";
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private String turnRight(String direction) {
        switch (direction) {
            case "N":
                return "E";
            case "E":
                return "S";
            case "S":
                return "W";
            case "W":
                return "N";
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    // Method to check if the next position is within the plateau bounds
    private boolean isValidPosition(int x, int y, int width, int height) {
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }

}
