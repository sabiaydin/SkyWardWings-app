package com.example.skyWardWingss.service.impl;

import com.example.skyWardWingss.dao.entity.Company;
import com.example.skyWardWingss.model.exceptions.child.CompanyNotFoundException;
import com.example.skyWardWingss.mapper.CompanyMapper;
import com.example.skyWardWingss.model.dto.request.CompanyRequestDto;
import com.example.skyWardWingss.model.dto.response.CompanyResponse;
import com.example.skyWardWingss.dao.repository.CompanyRepository;
import com.example.skyWardWingss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponse add(CompanyRequestDto companyRequestDto) {
        log.info("Add method for company is started");
        Company company = companyMapper.toCompany(companyRequestDto);
        Company savedCompany = companyRepository.save(company);
        log.info("Company added with id = {}", savedCompany.getId());
        return companyMapper.toCompanyResponse(savedCompany);
    }

    @Override
    public CompanyResponse update(Long id, CompanyRequestDto companyRequestDto) {
        log.info("Update method for company with id = {} is started", id);
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        Company updatedCompany = companyMapper.toCompany(companyRequestDto);
        updatedCompany.setId(existingCompany.getId());
        Company savedCompany = companyRepository.save(updatedCompany);
        log.info("Company with id = {} updated successfully", savedCompany.getId());
        return companyMapper.toCompanyResponse(savedCompany);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete method for company with id = {} is started", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        companyRepository.delete(company);
        log.info("Company with id = {} deleted successfully", id);
    }

    @Override
    public CompanyResponse getById(Long id) {
        log.info("Get method for company by id = {} is started", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        log.info("Company found with id = {}", id);
        return companyMapper.toCompanyResponse(company);
    }

    @Override
    public Page<CompanyResponse> getAll(Pageable pageable) {
        log.info("Get all method for companies is started with pagination");
        Page<Company> companyPage = companyRepository.findAll(pageable);
        Page<CompanyResponse> companyResponses = companyPage.map(companyMapper::toCompanyResponse);
        log.info("Found {} companies", companyResponses.getNumberOfElements());
        return companyResponses;
    }
}
