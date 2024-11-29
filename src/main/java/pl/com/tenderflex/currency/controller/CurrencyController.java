package pl.com.tenderflex.currency.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.currency.payload.CurrencyResponse;
import pl.com.tenderflex.currency.service.CurrencyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }
    
}