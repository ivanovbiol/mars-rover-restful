package com.mars.operation.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rovers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "plateau_id")
    private Plateau plateau;
}
