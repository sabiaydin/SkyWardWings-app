package com.example.skyWardWingss.dao.repository;

import com.example.skyWardWingss.dao.entity.Customer;
import com.example.skyWardWingss.dao.entity.Flight;
import com.example.skyWardWingss.dao.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsBySeatNumberAndFlight(String seatNumber, Flight flight);
    long countByCustomerAndFlight(Customer customer, Flight flight);
}
