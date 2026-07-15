package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;

public interface ContractRepository {

    Contract save(Contract contract);
    
    void update(Contract contract);
    
    Contract findById(Integer id);

    List<Contract> findAll(EContractStatus globalStatus);

    Contract findByAwardDecisionId(Integer id);
    
}