package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.payload.mapper.CompanyProfileMapper;
import com.flex.tender.payload.request.CompanyProfileRequest;
import com.flex.tender.repository.CompanyProfileRepository;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.CountryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;
    private final CompanyProfileMapper companyProfileMapper;
    private final CountryService countryService;

    @Override
    public CompanyProfile save(CompanyProfileRequest companyProfileRequest) {
        CompanyProfile companyProfile = companyProfileMapper.toEntity(companyProfileRequest);
        companyProfile.setCountry(countryService.findById(companyProfileRequest.countryId()));
        return companyProfileRepository.save(companyProfile);
    }

}