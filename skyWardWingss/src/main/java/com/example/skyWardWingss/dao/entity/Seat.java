package com.example.skyWardWingss.dao.entity;

import com.example.skyWardWingss.model.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "seats")
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private String seatType;
    private Double price;


    @ManyToOne
    @JoinColumn(name = "airplanes_id")
    private Airplane airplane;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('RESERVED','UNRESERVED')")
    private SeatStatus seatStatus;
}
