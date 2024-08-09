package com.example.skyWardWingss.service;

import com.example.skyWardWingss.model.dto.request.CompanyRequestDto;
import com.example.skyWardWingss.model.dto.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyResponse add(CompanyRequestDto companyRequestDto);

    CompanyResponse update(Long id, CompanyRequestDto companyRequestDto);

    void delete(Long id);

    CompanyResponse getById(Long id);

    Page<CompanyResponse> getAll(Pageable pageable);
}
