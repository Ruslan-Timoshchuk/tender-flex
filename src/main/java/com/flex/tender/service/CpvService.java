package com.flex.tender.service;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Cpv;
import com.flex.tender.payload.response.CpvResponse;

public interface CpvService {

    List<CpvResponse> findAll();

    Map<Integer, Cpv> findByOfferIdIn(List<Integer> offerIds);

}