package com.rover.app.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "plateau")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plateau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;
}
