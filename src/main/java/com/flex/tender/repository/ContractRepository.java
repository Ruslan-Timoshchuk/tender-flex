package com.flex.tender.repository;

import java.util.Set;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;

public interface ContractRepository {

    Contract save(Contract contract);
    
    void update(Contract contract);
    
    Contract findById(Integer id);

    Set<Contract> findAll(EContractStatus globalStatus);

    Contract findByAwardDecisionId(Integer id);
    
}