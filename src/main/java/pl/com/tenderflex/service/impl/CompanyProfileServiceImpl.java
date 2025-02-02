package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.repository.CompanyProfileRepository;
import pl.com.tenderflex.service.CompanyProfileService;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    @Override
    public CompanyProfile create(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

}