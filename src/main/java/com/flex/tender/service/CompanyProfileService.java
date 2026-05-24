package com.flex.tender.service;

import com.flex.tender.model.CompanyProfile;
import com.flex.tender.payload.request.CompanyProfileRequest;

public interface CompanyProfileService {

    CompanyProfile save(CompanyProfileRequest companyProfile);
    
}