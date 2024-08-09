package com.example.skyWardWingss.dao.repository;

import com.example.skyWardWingss.dao.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane,Long> {

}
