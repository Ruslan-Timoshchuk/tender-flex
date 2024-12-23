package pl.com.tenderflex.contract.service;

import pl.com.tenderflex.contract.payload.ContractRequest;
import pl.com.tenderflex.contract.payload.ContractResponse;

public interface ContractService {

    ContractResponse create(ContractRequest contract);

    ContractResponse findByTenderId(Integer id);
    
}