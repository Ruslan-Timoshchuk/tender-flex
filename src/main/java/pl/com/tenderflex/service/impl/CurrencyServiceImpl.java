package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CurrencyRepository;
import pl.com.tenderflex.payload.mapstract.CurrencyMapper;
import pl.com.tenderflex.payload.response.CurrencyResponse;
import pl.com.tenderflex.service.CurrencyService;

@Component
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.getAll().stream().map(currencyMapper::currencyToCurrencyResponce).toList();
    }
}