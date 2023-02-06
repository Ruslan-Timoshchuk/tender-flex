package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.BidderTenderResponse;
import pl.com.tenderflex.dto.ContractorTenderDetailsResponse;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.dto.ContractorTenderResponse;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId);

    Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage);

    Page<BidderTenderResponse> getByCondition(Integer currentPage);

    ContractorTenderDetailsResponse getById(Integer tenderId);

}