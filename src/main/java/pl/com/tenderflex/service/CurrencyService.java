package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.dto.CurrencyResponse;

public interface CurrencyService {

    List<CurrencyResponse> getAllCurrencies();
    
}