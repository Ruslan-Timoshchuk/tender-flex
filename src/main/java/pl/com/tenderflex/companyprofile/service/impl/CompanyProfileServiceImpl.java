package pl.com.tenderflex.companyprofile.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileMapper;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileRequest;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileResponse;
import pl.com.tenderflex.companyprofile.repository.CompanyProfileRepository;
import pl.com.tenderflex.companyprofile.service.CompanyProfileService;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final CompanyProfileMapper companyProfileMapper;
    private final CompanyProfileRepository companyProfileRepository;

    @Override
    public CompanyProfileResponse create(CompanyProfileRequest companyProfile) {
        return companyProfileMapper
                .toResponse(companyProfileRepository.save(companyProfileMapper.toEntity(companyProfile)));
    }

    @Override
    public CompanyProfileResponse findById(Integer id) {
        return companyProfileMapper.toResponse(companyProfileRepository.findById(id));
    }

}