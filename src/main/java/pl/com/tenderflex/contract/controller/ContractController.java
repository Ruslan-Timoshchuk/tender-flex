package pl.com.tenderflex.contract.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.contract.payload.ContractRequest;
import pl.com.tenderflex.contract.payload.ContractResponse;
import pl.com.tenderflex.contract.service.ContractService;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    
    @Secured("CONTRACTOR")
    @PostMapping
    public ContractResponse createContract(@RequestBody ContractRequest contractRequest) {
        return contractService.create(contractRequest);
    }
    
    @Secured("CONTRACTOR")
    @GetMapping("/{id}")
    public ContractResponse findByTenderId(@PathVariable Integer id) {
        return contractService.findByTenderId(id);
    }
    
}