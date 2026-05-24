package com.flex.tender.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Country;
import com.flex.tender.payload.mapper.CountryMapper;
import com.flex.tender.payload.response.CountryResponse;
import com.flex.tender.repository.CountryRepository;
import com.flex.tender.service.CountryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryResponse> findAll() {
        return countryRepository.findAll().stream().map(countryMapper::toResponse).toList();
    }

    @Override
    public Country findById(Integer id) {
        return countryRepository.findById(id);
    }

}