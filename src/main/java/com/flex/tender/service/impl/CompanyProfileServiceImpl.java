package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;

import com.flex.tender.model.CompanyProfile;
import com.flex.tender.repository.CompanyProfileRepository;
import com.flex.tender.service.CompanyProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    @Override
    public CompanyProfile create(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

}