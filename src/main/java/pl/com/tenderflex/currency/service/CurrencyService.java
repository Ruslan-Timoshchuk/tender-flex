package pl.com.tenderflex.currency.service;

import java.util.List;

import pl.com.tenderflex.currency.payload.CurrencyResponse;

public interface CurrencyService {

    List<CurrencyResponse> getAllCurrencies();
    
}