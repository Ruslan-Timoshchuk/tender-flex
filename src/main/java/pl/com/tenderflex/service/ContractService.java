package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.payload.request.InitiateContractSigningRequest;
import pl.com.tenderflex.payload.response.ContractResponse;

public interface ContractService {

    Contract save(Contract contract);

    ContractResponse findById(Integer id);

    ContractResponse initiateContractSigning(InitiateContractSigningRequest selectedOfferRequest);
    
}