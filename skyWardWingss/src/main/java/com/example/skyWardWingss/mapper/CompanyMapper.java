package com.example.skyWardWingss.mapper;

import com.example.skyWardWingss.dao.entity.Company;
import com.example.skyWardWingss.model.dto.request.CompanyRequestDto;
import com.example.skyWardWingss.model.dto.response.CompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toCompany(CompanyRequestDto companyRequestDto);

    CompanyResponse toCompanyResponse(Company savedCompany);
}
