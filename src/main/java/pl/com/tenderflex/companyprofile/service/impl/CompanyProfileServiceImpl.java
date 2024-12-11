package pl.com.tenderflex.companyprofile.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.model.CompanyProfile;
import pl.com.tenderflex.companyprofile.repository.CompanyProfileRepository;
import pl.com.tenderflex.companyprofile.service.CompanyProfileService;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

}