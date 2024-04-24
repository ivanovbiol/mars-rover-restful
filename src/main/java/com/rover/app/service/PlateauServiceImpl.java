package com.rover.app.service;

import com.rover.app.model.Plateau;
import com.rover.app.model.dto.PlateauCoordinates;
import com.rover.app.repository.PlateauRepository;
import com.rover.app.service.contract.PlateauService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PlateauServiceImpl implements PlateauService {

    private final PlateauRepository plateauRepository;

    @Override
    public Plateau create(PlateauCoordinates plateauCoordinates) {
        Plateau plateau = plateauRepository.save(Plateau.builder()
                .width(plateauCoordinates.getWidth())
                .height(plateauCoordinates.getHeight())
                .build());
        log.info("Plateau created");
        return plateau;
    }
}
