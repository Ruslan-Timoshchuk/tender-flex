package pl.com.tenderflex.controller;

import static java.util.Arrays.asList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.TenderType;

@RestController
@RequestMapping("/api/v1/global")
public class GlobalController {

    @GetMapping("/countries")
    public List<Country> getCountries(){
        return asList(Country.values());
    }
    
    @GetMapping("/tender-types")
    public List<TenderType> getTenderTypes(){
        return asList(TenderType.values());
    }
    
    @GetMapping("/currencies")
    public List<Currency> getCurrencies(){
        return asList(Currency.values());
    }
}