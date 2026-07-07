package com.flex.tender.service.read;

import java.util.List;
import com.flex.tender.model.Currency;
import com.flex.tender.payload.response.CurrencyResponse;

public interface CurrencyDetailsService {

    List<CurrencyResponse> findAll();

    Currency findById(Integer id);
    
}