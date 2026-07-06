package com.flex.tender.service.read;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.response.TenderDetailsResponse;

public interface TenderDetailsService {

    Tender findById(Integer id);

    TenderDetailsResponse loadTenderDetailsById(Integer tenderId);

    Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds);

}