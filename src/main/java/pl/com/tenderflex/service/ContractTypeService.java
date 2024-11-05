package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.payload.iresponse.response.ContractTypeResponse;

public interface ContractTypeService {

    List<ContractTypeResponse>getAll();
    
}