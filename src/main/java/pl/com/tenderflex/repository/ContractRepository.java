package pl.com.tenderflex.repository;

import java.util.Set;
import pl.com.tenderflex.model.Contract;

public interface ContractRepository {

    Contract save(Contract contract);
    
    void update(Contract contract);
    
    Contract findById(Integer id);

    Set<Contract> findAll(boolean hasSigned);
    
}