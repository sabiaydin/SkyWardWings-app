package com.example.skyWardWingss.dao.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TypePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id",referencedColumnName = "id")
    private Flight flight;

    private Integer price;
    private String type;

    public  TypePrice(Flight flight,Integer price,String type){
        this.flight = flight;
        this.price = price;
        this.type = type;
    }
}
