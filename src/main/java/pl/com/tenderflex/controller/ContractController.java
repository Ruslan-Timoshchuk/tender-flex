package pl.com.tenderflex.controller;

import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.request.InitiateContractSigningRequest;
import pl.com.tenderflex.payload.response.ContractResponse;
import pl.com.tenderflex.service.ContractService;

@RestController
@RequiredArgsConstructor
public class ContractController {

    public static final String URL_CONTRACTS_ID = "/api/v1/contracts/{id}";
    public static final String URL_CONTRACTS_SELECT_OFFER = "/api/v1/contracts/winning-offer";

    private final ContractService contractService;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_CONTRACTS_ID)
    public ContractResponse findById(@PathVariable("id") Integer id) {
        return contractService.findById(id);
    }

    @Secured(CONTRACTOR)
    @PatchMapping(URL_CONTRACTS_SELECT_OFFER)
    public ContractResponse selectWinningOffer(@RequestBody InitiateContractSigningRequest contractSigningRequest) {
        return contractService.initiateContractSigning(contractSigningRequest);
    }

}