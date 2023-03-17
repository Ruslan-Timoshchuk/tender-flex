package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CountryRepository;
import pl.com.tenderflex.payload.mapstract.CountryMapper;
import pl.com.tenderflex.payload.response.CountryResponse;
import pl.com.tenderflex.service.CountryService;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryResponse> getAllCountries() {
        return countryRepository.getAll().stream().map(countryMapper::countryToCountryResponse).toList();
    }
}