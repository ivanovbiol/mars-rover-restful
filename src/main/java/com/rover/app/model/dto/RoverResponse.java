package com.rover.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoverResponse {

    private int id;
    private int currentX;
    private int currentY;
    private String direction;
}
