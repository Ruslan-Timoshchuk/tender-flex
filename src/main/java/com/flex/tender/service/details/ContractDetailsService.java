package com.flex.tender.service.details;

import com.flex.tender.model.Contract;

public interface ContractDetailsService {

    Contract findByTenderId(Integer tenderId);

}
