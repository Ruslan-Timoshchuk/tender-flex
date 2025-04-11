package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.payload.response.ContractResponse;

public interface ContractService {

    Contract create(Contract contract);

    ContractResponse findById(Integer id);
    
}