package com.example.skyWardWingss.dao.entity;


import com.example.skyWardWingss.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "payments")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('FAILED','COMPLETED')")
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonBackReference
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "card_detail_id", referencedColumnName = "id")
    @JsonManagedReference
    private CardDetail cardDetail;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;
}
