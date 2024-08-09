package com.example.skyWardWingss.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Table(name = "cardDetail")
@Entity
public class CardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankAccount;
    private Integer cvv;
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "cardDetail", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Payment> payments;
}
