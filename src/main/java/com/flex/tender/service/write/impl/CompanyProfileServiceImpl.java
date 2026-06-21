package com.flex.tender.service.transactional.impl;

import org.springframework.stereotype.Service;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.payload.mapper.CompanyProfileMapper;
import com.flex.tender.payload.request.CompanyProfileRequest;
import com.flex.tender.repository.CompanyProfileRepository;
import com.flex.tender.service.CountryService;
import com.flex.tender.service.transactional.CompanyProfileService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;
    private final CompanyProfileMapper companyProfileMapper;
    private final CountryService countryService;

    @Override
    public CompanyProfile buildEntity(CompanyProfileRequest companyProfileRequest) {
        CompanyProfile companyProfile = companyProfileMapper.toEntity(companyProfileRequest);
        companyProfile.setCountry(countryService.findById(companyProfileRequest.countryId()));
        return companyProfile;
    }
    
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

}