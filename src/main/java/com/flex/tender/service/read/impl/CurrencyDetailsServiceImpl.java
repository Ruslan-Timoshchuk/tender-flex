package com.flex.tender.service.read.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Currency;
import com.flex.tender.payload.mapper.CurrencyMapper;
import com.flex.tender.payload.response.CurrencyResponse;
import com.flex.tender.repository.CurrencyRepository;
import com.flex.tender.service.read.CurrencyDetailsService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrencyDetailsServiceImpl implements CurrencyDetailsService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyResponse> findAll() {
        return currencyRepository.findAll().stream().map(currencyMapper::teResponse).toList();
    }

    @Override
    public Currency findById(Integer id) {
        return currencyRepository.findById(id);
    }

}