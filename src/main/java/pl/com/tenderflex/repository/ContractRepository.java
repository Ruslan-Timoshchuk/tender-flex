package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.Contract;

public interface ContractRepository {

    Contract save(Contract contract);
    
    Contract findById(Integer id);
    
}