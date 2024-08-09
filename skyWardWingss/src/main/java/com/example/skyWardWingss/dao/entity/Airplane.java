package com.example.skyWardWingss.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "airplanes")
@Entity
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer capacity;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Flight> flight;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Seat> seat;

}
