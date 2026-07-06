package com.flex.tender.service.read.impl;

import java.util.Set;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.service.read.ContractDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractDetailsServiceImpl implements ContractDetailsService {

    private final ContractRepository contractRepository;

    @Override
    public Contract findById(Integer id) {
        return contractRepository.findById(id);
    }
    
    @Override
    public Contract findByAwardDecisionId(Integer awardDecisionId) {
        return contractRepository.findByAwardDecisionId(awardDecisionId);
    }
    
    @Override
    public Set<Contract> findAll(EContractStatus globalStatus) {
        return contractRepository.findAll(globalStatus);
    }
    
}