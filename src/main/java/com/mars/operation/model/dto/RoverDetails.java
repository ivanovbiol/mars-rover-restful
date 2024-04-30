package com.mars.operation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoverDetails {

    @Positive(message = "Rover position must be positive")
    private int x;

    @Positive(message = "Rover position must be positive")
    private int y;

    @NotBlank(message = "Rover direction must not be empty")
    @NotNull(message = "Rover direction must not be null")
    private String direction;
}
