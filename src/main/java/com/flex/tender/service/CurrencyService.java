package com.flex.tender.service;

import java.util.List;
import com.flex.tender.model.Currency;
import com.flex.tender.payload.response.CurrencyResponse;

public interface CurrencyService {

    List<CurrencyResponse> findAll();

    Currency findById(Integer id);
    
}