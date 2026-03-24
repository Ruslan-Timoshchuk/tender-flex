package com.flex.tender.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.flex.tender.payload.mapstract.ContractTypeMapper;
import com.flex.tender.payload.response.ContractTypeResponse;
import com.flex.tender.repository.ContractTypeRepository;
import com.flex.tender.service.ContractTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeRepository contractTypeRepository;

    @Override
    public List<ContractTypeResponse> getAll() {
        return contractTypeRepository.getAll().stream().map(contractTypeMapper::toResponse)
                .toList();
    }
}