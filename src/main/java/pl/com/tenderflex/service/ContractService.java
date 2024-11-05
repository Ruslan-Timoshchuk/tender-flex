package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.iresponse.response.ContractResponse;
import pl.com.tenderflex.payload.request.ContractRequest;

public interface ContractService {

    ContractResponse create(ContractRequest contract);
    
}