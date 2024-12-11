package pl.com.tenderflex.companyprofile.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.payload.CountryMapper;
import pl.com.tenderflex.companyprofile.payload.CountryResponse;
import pl.com.tenderflex.companyprofile.repository.CountryRepository;
import pl.com.tenderflex.companyprofile.service.CountryService;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryResponse> getAllCountries() {
        return countryRepository.getAll().stream().map(countryMapper::toResponse).toList();
    }
    
}