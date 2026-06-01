package com.flex.tender.repository;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Cpv;

public interface CpvRepository {

    List<Cpv> findAll();

    Map<Integer, Cpv> findByTenderIdIn(List<Integer> tenderIds);
    
    Map<Integer, Cpv> findByOfferIdIn(List<Integer> offerIds);

    Cpv findById(Integer id);

    Cpv findByTenderId(Integer id);   

}