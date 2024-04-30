package com.mars.operation;

import com.mars.operation.model.CommandHistory;
import com.mars.operation.model.Direction;
import com.mars.operation.model.Plateau;
import com.mars.operation.model.Rover;
import com.mars.operation.model.dto.RoverDetails;
import com.mars.operation.model.dto.RoverResponse;
import com.mars.operation.repository.CommandHistoryRepository;
import com.mars.operation.repository.DirectionRepository;
import com.mars.operation.repository.PlateauRepository;
import com.mars.operation.repository.RoverRepository;
import com.mars.operation.service.RoverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoverServiceImplTest {

    @Mock
    private CommandHistoryRepository commandHistoryRepository;

    @Mock
    private PlateauRepository plateauRepository;

    @Mock
    private RoverRepository roverRepository;

    @Mock
    private DirectionRepository directionRepository;

    @InjectMocks
    private RoverServiceImpl roverService;

    @Test
    public void create_should_call_repository() {
        RoverDetails roverDetails = new RoverDetails(1, 2, "N");
        Direction direction = new Direction(1, "N");

        when(directionRepository.findByName(roverDetails.getDirection())).thenReturn(direction);
        Plateau plateau = Plateau.builder().id(1).height(5).width(5).build();
        Rover savedRover = new Rover(1, 1, 2, direction, plateau);
        when(roverRepository.save(argThat(rover -> rover.getX() == 1 && rover.getY() == 2))).thenReturn(savedRover);

        Rover createdRover = roverService.create(roverDetails);

        verify(roverRepository).save(any(Rover.class));

        assertEquals(savedRover, createdRover);
    }

    @Test
    public void link_should_findPlateau_when_available() {
        int plateauId = 1;
        Plateau plateau = new Plateau(plateauId, 5, 5);
        when(plateauRepository.findById(plateauId)).thenReturn(Optional.of(plateau));

        Plateau foundPlateau = plateauRepository.findById(plateauId).get();

        verify(plateauRepository).findById(plateauId);
        assertEquals(plateau, foundPlateau);
    }

    @Test
    public void link_should_throw_when_plateauIsNotAvailable() {
        when(plateauRepository.findById(1)).thenThrow(new IllegalStateException("No such plateau"));

        assertThrows(IllegalStateException.class, () -> roverService.linkToPlateau(1, 1));
    }

    @Test
    public void link_should_returnRover() {
        int plateauId = 1;
        int roverId = 1;
        Plateau plateau = new Plateau(plateauId, 5, 5);
        Rover rover = new Rover(roverId, 0, 0, new Direction(1, "N"), plateau);
        when(plateauRepository.findById(plateauId)).thenReturn(Optional.of(plateau));
        when(roverRepository.findById(roverId)).thenReturn(Optional.of(rover));
        when(roverRepository.save(rover)).thenReturn(rover);

        Rover linkedRover = roverService.linkToPlateau(roverId, plateauId);

        assertNotNull(linkedRover);
        assertEquals(rover, linkedRover);
    }

    @Test
    public void link_should_throw_when_roverIsNotAvailable() {
        int plateauId = 1;
        Plateau plateau = new Plateau(plateauId, 5, 5);
        when(plateauRepository.findById(plateauId)).thenReturn(Optional.of(plateau));
        when(roverRepository.findById(1)).thenThrow(new IllegalStateException("No such Rover"));

        assertThrows(IllegalStateException.class, () -> roverService.linkToPlateau(1, 1));
    }


    @Test
    public void moveRover_should_returnRoverResponse() {
        int roverId = 1;
        String instructions = "MLMR";
        Direction direction = new Direction(1, "N");
        Plateau plateau = new Plateau(1, 5, 5);
        Rover rover = new Rover(roverId, 1, 2, direction, plateau);
        CommandHistory commandHistory = CommandHistory.builder()
                .id(1)
                .currentRoverX(1)
                .currentRoverY(2)
                .instruction(instructions)
                .direction(direction)
                .rover(rover)
                .executed(false).build();
        when(roverRepository.findById(roverId)).thenReturn(Optional.of(rover));
        when(plateauRepository.findById(rover.getPlateau().getId())).thenReturn(Optional.of(plateau));
        when(directionRepository.findByName(anyString())).thenReturn(new Direction());
        when(roverRepository.save(any(Rover.class))).thenReturn(rover);
        when(commandHistoryRepository.save(any(CommandHistory.class))).thenReturn(commandHistory);
        RoverResponse roverResponse = roverService.move(roverId, instructions);

        assertNotNull(roverResponse);
        assertEquals(roverId, roverResponse.getRoverId());
    }

    @Test
    public void moveRover_should_throw_when_roverNotFounde() {
        int roverId = 1;
        String instructions = "MLMR";
        when(roverRepository.findById(roverId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> roverService.move(roverId, instructions));
    }

    @Test
    public void moveRover_should_throw_when_PlateauNotFounde() {
        int roverId = 1;
        String instructions = "MLMR";
        Plateau plateau = new Plateau(1, 5, 5);
        Rover rover = new Rover(roverId, 1, 2, new Direction(1, "N"), plateau);
        when(roverRepository.findById(roverId)).thenReturn(Optional.of(rover));
        when(plateauRepository.findById(rover.getPlateau().getId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> roverService.move(roverId, instructions));
    }

}

