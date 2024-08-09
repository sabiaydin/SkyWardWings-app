package com.example.skyWardWingss.dao.repository;


import com.example.skyWardWingss.dao.entity.TypePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePriceRepository extends JpaRepository<TypePrice,Long> {

    @Query("select t from TypePrice t where t.flight.id=?1 and t.type=?2")
    TypePrice findByFlightIdAndType(Long flightId, String seatType);

}
