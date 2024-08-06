package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.CpvResponse;
import pl.com.tenderflex.service.CpvService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpv")
public class CpvController {

    private final CpvService cpvService;
    
    @Secured("CONTRACTOR")
    @GetMapping("/list")
    public List<CpvResponse> getAllCountries() {
        return cpvService.getAllCPVs();
    }
}