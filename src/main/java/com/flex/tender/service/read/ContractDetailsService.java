package com.flex.tender.service.read;

import java.util.List;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;

public interface ContractDetailsService {

    Contract findById(Integer id);
    
    Contract findByAwardDecisionId(Integer awardDecisionId);

    List<Contract> findAll(EContractStatus globalStatus);

}