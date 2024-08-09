package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.CardDetail;
import com.example.skyWardWingss.model.dto.request.CardDetailedDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardDetailMapper {
    CardDetail toCardDetail(CardDetailedDto cardDetailedDto);
}
