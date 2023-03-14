package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, Integer contractorId);

    Integer getTendersAmountByContractor(Integer contractorId);

    List<ContractorTenderResponse> getByContractor(Integer contractorId, Integer amountTenders,
            Integer amountTendersToSkip);

    Page<BidderTenderResponse> getByCondition(Integer currentPage);

    ContractorTenderDetailsResponse getById(Integer tenderId);

}