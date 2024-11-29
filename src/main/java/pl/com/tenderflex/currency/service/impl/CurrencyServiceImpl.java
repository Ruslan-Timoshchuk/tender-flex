package pl.com.tenderflex.currency.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.currency.payload.CurrencyResponse;
import pl.com.tenderflex.currency.repository.CurrencyRepository;
import pl.com.tenderflex.currency.service.CurrencyService;

@Component
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(CurrencyResponse::new).toList();
    }
    
}