package pl.com.tenderflex.repository;

import java.util.List;

import pl.com.tenderflex.model.ContractType;

public interface ContractTypeRepository {

    List<ContractType> getAll();
    
}