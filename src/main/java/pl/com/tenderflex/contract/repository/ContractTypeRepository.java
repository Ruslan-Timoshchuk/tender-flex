package pl.com.tenderflex.contract.repository;

import java.util.List;

import pl.com.tenderflex.contract.model.ContractType;

public interface ContractTypeRepository {

    List<ContractType> getAll();
    
}