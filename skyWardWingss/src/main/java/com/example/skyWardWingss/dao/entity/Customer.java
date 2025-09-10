package com.example.skyWardWingss.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "customers")
@ToString(exclude = {"reservation", "user", "cardDetail"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List <Reservation>  reservation;


    @OneToOne(mappedBy = "customer",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CardDetail> cardDetail;


}
