package com.rover.app.service.contract;

import com.rover.app.model.Plateau;
import com.rover.app.model.dto.PlateauCoordinates;

public interface PlateauService {
    Plateau create(PlateauCoordinates plateauCoordinates);
}
