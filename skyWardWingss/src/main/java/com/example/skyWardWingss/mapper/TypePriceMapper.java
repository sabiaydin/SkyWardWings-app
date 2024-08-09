    package com.example.skyWardWingss.mapper;

    import com.example.skyWardWingss.dao.entity.TypePrice;
    import com.example.skyWardWingss.model.dto.request.TypePriceUpdateDto;
    import org.mapstruct.Mapper;
    import org.mapstruct.MappingTarget;

    @Mapper(componentModel = "spring")
    public interface TypePriceMapper {
        TypePrice totypePrice(TypePriceUpdateDto typePriceUpdateDto);
        void updateTypePriceFromDto(@MappingTarget TypePrice typePrice, TypePriceUpdateDto typePriceUpdateDto);

    }
