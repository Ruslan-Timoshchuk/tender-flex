package pl.com.tenderflex.companyprofile.repository;

import pl.com.tenderflex.companyprofile.model.CompanyProfile;

public interface CompanyProfileRepository {

    CompanyProfile save(CompanyProfile companyProfile);
    
}