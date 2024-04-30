package com.mars.operation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoverResponse {

    private int roverId;
    private int currentX;
    private int currentY;
    private String direction;
}
