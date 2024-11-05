package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.Contract;

public interface ContractRepository {

    Contract save(Contract contract);
    
}