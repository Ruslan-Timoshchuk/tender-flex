package com.flex.tender.service.read.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Cpv;
import com.flex.tender.payload.mapper.CpvMapper;
import com.flex.tender.payload.response.CpvResponse;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.service.read.CpvService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpvServiceImpl implements CpvService {

    public static final String CPV_WITH_ID_NOT_FOUND = "Cpv with [id: %s] not found.";
    
    private final CpvRepository cpvRepository;
    private final CpvMapper cpvMapper;

    @Override
    public List<CpvResponse> findAll() {
        return cpvRepository
                  .findAll()
                  .stream()
                  .map(cpvMapper::toResponse)
                  .toList();
    }

    @Override
    public Cpv findById(Integer id) {
        return cpvRepository.findById(id);
    }

}