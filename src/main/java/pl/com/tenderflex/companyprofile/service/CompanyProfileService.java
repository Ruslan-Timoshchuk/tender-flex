package pl.com.tenderflex.companyprofile.service;

import pl.com.tenderflex.companyprofile.payload.CompanyProfileRequest;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileResponse;

public interface CompanyProfileService {

    CompanyProfileResponse create(CompanyProfileRequest companyProfile);

    CompanyProfileResponse findById(Integer id);
    
}