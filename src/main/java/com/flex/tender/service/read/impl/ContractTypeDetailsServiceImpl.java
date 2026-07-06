package com.flex.tender.service.read.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.flex.tender.model.ContractType;
import com.flex.tender.payload.mapper.ContractTypeMapper;
import com.flex.tender.payload.response.ContractTypeResponse;
import com.flex.tender.repository.ContractTypeRepository;
import com.flex.tender.service.read.ContractTypeDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractTypeDetailsServiceImpl implements ContractTypeDetailsService {

    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeRepository contractTypeRepository;

    @Override
    public List<ContractTypeResponse> findAll() {
        return contractTypeRepository.findAll().stream().map(contractTypeMapper::toResponse).toList();
    }

    @Override
    public ContractType findById(Integer id) {
        return contractTypeRepository.findById(id);
    }
    
}