package com.flex.tender.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Cpv;
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
    public List<CpvResponse> findAll() {
        return cpvRepository
                  .findAll()
                  .stream()
                  .map(cpvMapper::toResponse)
                  .toList();
    }

    @Override
    public Map<Integer, Cpv> findByOfferIdIn(List<Integer> offerIds) {
        return cpvRepository.findByOfferIdIn(offerIds);
    }

}