package pl.com.tenderflex.tender.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.tender.payload.CpvResponse;
import pl.com.tenderflex.tender.service.CpvService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cpvs")
public class CpvController {

    private final CpvService cpvService;
    
    @Secured("CONTRACTOR")
    @GetMapping
    public List<CpvResponse> getAllCpvs() {
        return cpvService.getAllCpvs();
    }
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/{id}")
    public CpvResponse getById(@PathVariable Integer id) {
        return cpvService.getById(id);
    }
    
}