package com.flex.tender.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.flex.tender.payload.mapper.CpvMapper;
import com.flex.tender.payload.response.CpvResponse;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.service.CpvService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpvServiceImpl implements CpvService {

    private final CpvRepository cpvRepository;
    private final CpvMapper cpvMapper;

    @Override
    public List<CpvResponse> getAllCpvs() {
        return cpvRepository.findAll().stream().map(cpvMapper::toResponse).toList();
    }

}