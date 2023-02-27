package pl.com.tenderflex.controller;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dto.CountryResponse;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.TenderType;
import pl.com.tenderflex.service.CountryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/global")
public class GlobalController {

    private final CountryService countryService;
    
    @GetMapping("/countries")
    @ResponseStatus(OK)
    public List<CountryResponse> getAllCountries(){
        return countryService.getAllCountries();
    }
    
    @GetMapping("/tender-types")
    @ResponseStatus(OK)
    public List<TenderType> getTenderTypes(){
        return asList(TenderType.values());
    }
    
    @GetMapping("/currencies")
    @ResponseStatus(OK)
    public List<Currency> getCurrencies(){
        return asList(Currency.values());
    }
}