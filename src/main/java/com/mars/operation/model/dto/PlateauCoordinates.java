package com.mars.operation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlateauCoordinates {

    @Positive(message = "Plateau width must be positive")
    private int width;

    @Positive(message = "Plateau height must be positive")
    private int height;
}
