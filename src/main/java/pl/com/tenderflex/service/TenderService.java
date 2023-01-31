package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.dto.TenderDetailsResponse;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId);

    Page<TenderDetailsResponse> getByContractor(Integer contractorId, Integer currentPage);

    Page<TenderDetailsResponse> getByCondition(Integer currentPage);

}