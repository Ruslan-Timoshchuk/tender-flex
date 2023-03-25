package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.CPVresponse;
import pl.com.tenderflex.service.CPVService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpv")
public class CPVController {

    private final CPVService cpvService;
    
    @Secured("CONTRACTOR")
    @GetMapping("/list")
    public List<CPVresponse> getAllCountries() {
        return cpvService.getAllCPVs();
    }
}