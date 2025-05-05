package pl.com.tenderflex.controller;

import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.ContractResponse;
import pl.com.tenderflex.service.ContractService;

@RestController
@RequiredArgsConstructor
public class ContractController {

    public static final String URL_CONTRACTS_ID = "/api/v1/contracts/{id}";
   
    private final ContractService contractService;
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_CONTRACTS_ID)
    public ContractResponse findById(@PathVariable("id") Integer id) {
        return contractService.findDetailsById(id);
    }
    
}