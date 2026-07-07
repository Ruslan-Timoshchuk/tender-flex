package com.flex.tender.service.read;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Tender;

public interface TenderDetailsBatchService {

    Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds);

}