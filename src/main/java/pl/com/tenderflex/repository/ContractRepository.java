package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.Contract;

public interface ContractRepository {

    Contract save(Contract contract);
    
    void update(Contract contract);
    
    Contract findById(Integer id);
    
}