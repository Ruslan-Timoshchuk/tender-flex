package com.flex.tender.service.write;

import com.flex.tender.model.CompanyProfile;
import com.flex.tender.payload.request.CompanyProfileRequest;

public interface CompanyProfileService {

    CompanyProfile buildEntity(CompanyProfileRequest companyProfileRequest);

    CompanyProfile save(CompanyProfile companyProfile);
    
}