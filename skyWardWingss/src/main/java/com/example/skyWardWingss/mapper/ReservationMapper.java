package com.example.skyWardWingss.mapper;
import com.example.skyWardWingss.model.dto.request.ReservationRequestDto;
import com.example.skyWardWingss.model.dto.response.ReservationResponse;
import com.example.skyWardWingss.dao.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FlightMapper.class})
public interface ReservationMapper {

    @Mapping(source = "id", target = "reservationId")
    ReservationResponse toReservationDto(Reservation reservation);

    Reservation toReservation(ReservationRequestDto reservationRequestDto);


}
