package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.CountryResponse;
import pl.com.tenderflex.service.CountryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping
    public List<CountryResponse> getAllCountries() {
        return countryService.getAllCountries();
    }
    
}