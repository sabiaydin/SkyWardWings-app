package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.Seat;
import com.example.skyWardWingss.model.dto.request.SeatRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {
    Seat toSeat(SeatRequest seatRequest);;

    void updateSeatFromDto(@MappingTarget Seat seat, SeatRequest seatRequest);
}
