package pl.com.tenderflex.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.ContractResponse;
import pl.com.tenderflex.payload.request.ContractRequest;
import pl.com.tenderflex.service.ContractService;

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
    
}