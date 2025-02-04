package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.ContractTypeResponse;
import pl.com.tenderflex.service.ContractTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contract-types")
public class ContractTypeController {

    public static final String URL_CONTRACT_TYPES_ALL = "/all";
    
    private final ContractTypeService contractTypeService;
    
    @Secured("CONTRACTOR")
    @GetMapping(URL_CONTRACT_TYPES_ALL)
    public List<ContractTypeResponse> getAllContractTypes() {
        return contractTypeService.getAll();
    }
    
}