package com.flex.tender.service;

import java.util.List;
import com.flex.tender.payload.response.ContractTypeResponse;

public interface ContractTypeService {

    List<ContractTypeResponse> findAll();

}