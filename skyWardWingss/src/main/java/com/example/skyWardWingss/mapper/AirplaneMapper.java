package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.Airplane;
import com.example.skyWardWingss.model.dto.request.AirplaneRequestDto;
import com.example.skyWardWingss.model.dto.request.AirplaneUpdateDto;
import com.example.skyWardWingss.model.dto.response.AirplaneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AirplaneMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seat", ignore = true)
    void updateAirplaneFromDto(@MappingTarget Airplane airplane, AirplaneUpdateDto airplaneUpdateDto);
    Airplane toAirplane(AirplaneRequestDto airplaneRequestDto);
    AirplaneResponse toAirplaneDto(Airplane airplane);
}
