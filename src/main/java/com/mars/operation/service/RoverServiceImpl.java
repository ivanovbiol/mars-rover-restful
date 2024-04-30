package com.mars.operation.service;

import com.mars.operation.model.CommandHistory;
import com.mars.operation.model.Plateau;
import com.mars.operation.model.Rover;
import com.mars.operation.model.dto.RoverDetails;
import com.mars.operation.model.dto.RoverResponse;
import com.mars.operation.repository.CommandHistoryRepository;
import com.mars.operation.repository.DirectionRepository;
import com.mars.operation.repository.PlateauRepository;
import com.mars.operation.repository.RoverRepository;
import com.mars.operation.service.contract.RoverService;
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
                .direction(directionRepository.findByName(roverDetails.getDirection()))
                .build());
        log.info("Rover created");
        return rover;
    }

    @Override
    public Rover linkToPlateau(int roverId, int plateauId) {
        Plateau plateau = plateauRepository.findById(plateauId).orElseThrow(() -> new IllegalStateException("No such plateau"));
        Rover rover = roverRepository.findById(roverId).orElseThrow(() -> new IllegalArgumentException("No such Rover"));
        rover.setPlateau(plateau);
        log.info(String.format("Plateau %d linked to Rover %d", plateauId, roverId));
        return roverRepository.save(rover);
    }

    @Override
    @Transactional
    public RoverResponse move(int id, String instructions) {
        // Find the rover from the repository
        Rover rover = roverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such Rover"));

        // Retrieve the plateau details
        Plateau plateau = plateauRepository.findById(rover.getPlateau().getId())
                .orElseThrow(() -> new IllegalStateException("Rover is not on any plateau"));

        // Move the rover based on the instructions
        moveRover(rover, instructions, plateau);

        // Create and return the response DTO
        return createRoverResponse(roverRepository.save(rover));
    }

    private RoverResponse createRoverResponse(Rover rover) {
        return RoverResponse.builder()
                .roverId(rover.getId())
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

                switch (direction) {
                    case "N":
                        nextY++;
                        break;
                    case "E":
                        nextX++;
                        break;
                    case "S":
                        nextY--;
                        break;
                    case "W":
                        nextX--;
                        break;
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
