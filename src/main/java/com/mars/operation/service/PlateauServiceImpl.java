package com.mars.operation.service;

import com.mars.operation.model.Plateau;
import com.mars.operation.model.dto.PlateauCoordinates;
import com.mars.operation.repository.PlateauRepository;
import com.mars.operation.service.contract.PlateauService;
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
