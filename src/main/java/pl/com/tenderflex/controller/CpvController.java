package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.CpvResponse;
import pl.com.tenderflex.service.CpvService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpvs")
public class CpvController {

    public static final String URL_CPVS_ALL = "/all";
    
    private final CpvService cpvService;
    
    @Secured("CONTRACTOR")
    @GetMapping(URL_CPVS_ALL)
    public List<CpvResponse> getAllCpvs() {
        return cpvService.getAllCpvs();
    }
    
}