package com.flex.tender.service.read;

import com.flex.tender.model.Contract;

public interface ContractDetailsService {

    Contract findByAwardDecisionId(Integer awardDecisionId);

}
