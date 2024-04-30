package com.mars.operation.service.contract;

import com.mars.operation.model.Plateau;
import com.mars.operation.model.dto.PlateauCoordinates;

public interface PlateauService {
    Plateau create(PlateauCoordinates plateauCoordinates);
}
