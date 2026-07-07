package com.flex.tender.service.read;

import java.util.Set;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;

public interface ContractDetailsService {

    Contract findById(Integer id);
    
    Contract findByAwardDecisionId(Integer awardDecisionId);

    Set<Contract> findAll(EContractStatus globalStatus);

}