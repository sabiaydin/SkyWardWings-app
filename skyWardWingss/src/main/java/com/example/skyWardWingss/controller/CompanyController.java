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
        return ResponseEntity.ok(companyService.add(companyRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @RequestBody CompanyRequestDto companyRequestDto) {
        return ResponseEntity.ok(companyService.update(id, companyRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(Pageable pageable) {
        return ResponseEntity.ok(companyService.getAll(pageable));
    }
}
