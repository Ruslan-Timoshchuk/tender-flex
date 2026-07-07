package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.ContractType;

public interface ContractTypeRepository {

    List<ContractType> findAll();

    ContractType findById(Integer id);
    
}