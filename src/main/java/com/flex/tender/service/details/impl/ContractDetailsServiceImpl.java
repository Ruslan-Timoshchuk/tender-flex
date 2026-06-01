package com.flex.tender.service.details.impl;

import org.springframework.stereotype.Service;
import com.flex.tender.model.Contract;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.service.details.ContractDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractDetailsServiceImpl implements ContractDetailsService {

    private final ContractRepository contractRepository;
    
    @Override
    public Contract findByTenderId(Integer tenderId) {
        return contractRepository.findByTenderId(tenderId);
    }
    
}