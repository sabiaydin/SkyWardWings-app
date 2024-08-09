package com.example.skyWardWingss.dao.entity;
import com.example.skyWardWingss.model.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "reservations")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private Double price;




    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonBackReference
    private Flight flight;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CANCELED','PENDING','CONFIRMED') DEFAULT 'PENDING'")
    private ReservationStatus reservationStatus=ReservationStatus.PENDING;
}
