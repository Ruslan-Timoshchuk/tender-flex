package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.CurrencyResponse;
import pl.com.tenderflex.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/list")
    public List<CurrencyResponse> getCurrencies() {
        return currencyService.getAllCurrencies();
    }
}