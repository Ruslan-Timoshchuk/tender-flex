package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.CompanyProfile;

public interface CompanyProfileRepository {
    
    CompanyProfile save(CompanyProfile companyProfile);
    
}