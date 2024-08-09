package com.example.skyWardWingss.dao.repository;


import com.example.skyWardWingss.dao.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findByDepartureAndDestinationAndDepartureTimeBetween(String departure, String destination, LocalDateTime startDateTime,LocalDateTime endDateTime);
}
