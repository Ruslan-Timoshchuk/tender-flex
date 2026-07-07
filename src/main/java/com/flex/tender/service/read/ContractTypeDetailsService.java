package com.flex.tender.service.read;

import java.util.List;
import com.flex.tender.model.ContractType;
import com.flex.tender.payload.response.ContractTypeResponse;

public interface ContractTypeDetailsService {

    List<ContractTypeResponse> findAll();

    ContractType findById(Integer id);

}