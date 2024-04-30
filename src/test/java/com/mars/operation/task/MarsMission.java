package com.mars.operation.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarsMission {

    @Test
    public void testMarsRover() {
        int plateauWidth = 5;
        int plateauHeight = 5;
        String[] roverPositions = {"1 2 N", "3 3 E"};
        String[] roverInstructions = {"LMLMLMLMM", "MMRMMRMRRM"};

        String expectedOutput = "1 3 N\n" + "5 1 E";

        String actualOutput = moveRover(plateauWidth, plateauHeight, roverPositions, roverInstructions);

        assertEquals(expectedOutput, actualOutput);
    }

    // Method to move the rover based on the instructions
    private String moveRover(int plateauWidth, int plateauHeight, String[] roverPositions, String[] roverInstructions) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < roverPositions.length; i++) {
            String[] roverPosition = roverPositions[i].split(" ");
            int x = Integer.parseInt(roverPosition[0]);
            int y = Integer.parseInt(roverPosition[1]);
            char direction = roverPosition[2].charAt(0);

            for (char instruction : roverInstructions[i].toCharArray()) {
                if (instruction == 'L') {
                    direction = turnLeft(direction);
                } else if (instruction == 'R') {
                    direction = turnRight(direction);
                } else if (instruction == 'M') {
                    if (direction == 'N' && y < plateauHeight) {
                        y++;
                    } else if (direction == 'E' && x < plateauWidth) {
                        x++;
                    } else if (direction == 'S' && y > 0) {
                        y--;
                    } else if (direction == 'W' && x > 0) {
                        x--;
                    }
                }
            }

            output.append(x).append(" ").append(y).append(" ").append(direction).append("\n");
        }

        return output.toString().trim();
    }

    private char turnLeft(char direction) {
        switch (direction) {
            case 'N':
                return 'W';
            case 'E':
                return 'N';
            case 'S':
                return 'E';
            case 'W':
                return 'S';
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private char turnRight(char direction) {
        switch (direction) {
            case 'N':
                return 'E';
            case 'E':
                return 'S';
            case 'S':
                return 'W';
            case 'W':
                return 'N';
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}
