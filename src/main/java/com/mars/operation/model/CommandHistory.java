package com.mars.operation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "command_history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommandHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "current_rover_x")
    private int currentRoverX;

    @Column(name = "current_rover_y")
    private int currentRoverY;

    @Column(name = "instruction")
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "rover_id")
    private Rover rover;

    @Column(name = "executed")
    private boolean executed;
}
