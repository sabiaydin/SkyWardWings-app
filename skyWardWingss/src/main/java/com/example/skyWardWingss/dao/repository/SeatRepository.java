package com.example.skyWardWingss.dao.repository;


import com.example.skyWardWingss.dao.entity.Airplane;
import com.example.skyWardWingss.dao.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {

    Optional<Seat> findFirstBySeatNumberAndAirplane(String seatNumber, Airplane airplane);

}
