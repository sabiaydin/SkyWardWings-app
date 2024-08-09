package com.example.skyWardWingss.mapper;


import com.example.skyWardWingss.model.dto.request.FlightRequestDto;
import com.example.skyWardWingss.model.dto.request.FlightUpdateDto;
import com.example.skyWardWingss.model.dto.response.FlightResponse;
import com.example.skyWardWingss.dao.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "typePrice", ignore = true)
    void updateFlightFromDto(@MappingTarget Flight flight, FlightUpdateDto flightUpdateDto);

    @Mapping(source = "flight",target = "duration",qualifiedByName = "mapToDuration")
    @Mapping(source = "flight.airplane", target = "airplane")
    @Mapping(source = "typePrice", target = "typePrice")
    FlightResponse toFlightDto(Flight flight);

    Flight toFlight(FlightRequestDto flightRequestDto);


    @Named("mapToDuration")
    default String mapToDuration(Flight flight){
            Duration flightDuration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
            long days = flightDuration.toDays();
            long hours = flightDuration.toHours() % 24;
            long minutes = flightDuration.toMinutes() % 60;

            String durationString;
            if (days > 0) {
                durationString = String.format("%d day %d hour %d minute", days, hours, minutes);
            } else {
                durationString = String.format("%d hour %d minute", hours, minutes);
            }

return durationString;
    }
}
