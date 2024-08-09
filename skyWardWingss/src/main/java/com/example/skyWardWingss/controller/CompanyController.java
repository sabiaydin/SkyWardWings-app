package com.example.skyWardWingss.controller;

import com.example.skyWardWingss.model.dto.request.CompanyRequestDto;
import com.example.skyWardWingss.model.dto.response.CompanyResponse;
import com.example.skyWardWingss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    @PostMapping
    public ResponseEntity<CompanyResponse> addCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        CompanyResponse companyResponse = companyService.add(companyRequestDto);
        return ResponseEntity.ok(companyResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDto companyRequestDto) {
        CompanyResponse companyResponse = companyService.update(id, companyRequestDto);
        return ResponseEntity.ok(companyResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        CompanyResponse companyResponse = companyService.getById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(Pageable pageable) {
        Page<CompanyResponse> companyResponses = companyService.getAll(pageable);
        return ResponseEntity.ok(companyResponses);
    }
}
