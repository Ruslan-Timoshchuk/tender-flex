package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CountryRepository;
import pl.com.tenderflex.dto.CountryResponse;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.service.CountryService;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final MapStructMapper countryMapper;

    public List<CountryResponse> getAllCountries() {
        return countryRepository.getAll().stream().map(countryMapper::countryToCountryResponse).toList();
    }
}