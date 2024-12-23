package pl.com.tenderflex.contract.repository;

import pl.com.tenderflex.contract.model.Contract;

public interface ContractRepository {

    Contract save(Contract contract);

    Contract findByTenderId(Integer id);
    
}