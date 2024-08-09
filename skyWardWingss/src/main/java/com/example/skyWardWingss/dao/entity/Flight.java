package com.example.skyWardWingss.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "flights")
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departure;
    private LocalDateTime departureTime;
    private String destination;
    private LocalDateTime arrivalTime;
    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;

    @OneToMany(mappedBy = "flight",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Reservation> reservation;

    @OneToMany(mappedBy = "flight",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<TypePrice> typePrice;


}
