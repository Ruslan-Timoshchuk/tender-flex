package com.flex.tender.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;

import com.flex.tender.payload.mapper.CurrencyMapper;
import com.flex.tender.payload.response.CurrencyResponse;
import com.flex.tender.repository.CurrencyRepository;
import com.flex.tender.service.CurrencyService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(currencyMapper::teResponse).toList();
    }

}