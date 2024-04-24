package com.rover.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoverController {

    @GetMapping("/rover")
    public String health() {
        return "Healthy rover";
    }
}
