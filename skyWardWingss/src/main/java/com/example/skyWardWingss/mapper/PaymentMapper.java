package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.CardDetail;
import com.example.skyWardWingss.dao.entity.Payment;
import com.example.skyWardWingss.model.dto.request.CardDetailedDto;
import com.example.skyWardWingss.model.dto.request.PaymentRequestDto;
import com.example.skyWardWingss.model.dto.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReservationMapper.class, CompanyMapper.class})
public interface PaymentMapper {
    Payment toPayment(PaymentRequestDto paymentRequestDto);
    @Mapping(source = "reservation", target = "reservation")
    @Mapping(source = "company", target = "company")
    PaymentResponse toPaymentDto(Payment payment);

    CardDetail toCardDetail(CardDetailedDto cardDetailedDto);
}
