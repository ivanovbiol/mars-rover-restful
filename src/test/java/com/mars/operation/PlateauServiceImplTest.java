package com.mars.operation;

import com.mars.operation.model.Plateau;
import com.mars.operation.model.dto.PlateauCoordinates;
import com.mars.operation.repository.PlateauRepository;
import com.mars.operation.service.PlateauServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlateauServiceImplTest {

    @Mock
    private PlateauRepository plateauRepository;

    @InjectMocks
    private PlateauServiceImpl plateauService;

    @Test
    public void testCreatePlateau() {
        PlateauCoordinates plateauCoordinates = new PlateauCoordinates(5, 5);
        Plateau savedPlateau = new Plateau(1, 5, 5);

        when(plateauRepository.save(any(Plateau.class))).thenReturn(savedPlateau);

        Plateau createdPlateau = plateauService.create(plateauCoordinates);

        verify(plateauRepository).save(any(Plateau.class));

        assertEquals(savedPlateau, createdPlateau);
    }
}
