package pl.com.tenderflex.contract.service;

import java.util.List;

import pl.com.tenderflex.contract.payload.ContractTypeResponse;

public interface ContractTypeService {

    List<ContractTypeResponse>getAll();
    
}