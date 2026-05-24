package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;
import com.flex.tender.model.Tender;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.TenderDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenderDetailsServiceImpl implements TenderDetailsService{

    private final TenderRepository tenderRepository;
    
    @Override
    public Tender findById(Integer id) {
        return tenderRepository.findById(id);
    }
    
}