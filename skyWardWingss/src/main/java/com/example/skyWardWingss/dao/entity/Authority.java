package com.example.skyWardWingss.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "authorities")
@Entity
public class Authority {
    @Id
    private String name;
}
