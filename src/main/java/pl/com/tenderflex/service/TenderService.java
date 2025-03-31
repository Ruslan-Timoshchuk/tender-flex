package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderRequest;
import pl.com.tenderflex.payload.response.TenderCountResponse;
import pl.com.tenderflex.payload.response.TenderResponse;

public interface TenderService {

    TenderResponse save(TenderRequest tenderRequest);

    TenderResponse findById(Integer tenderId);

    TenderCountResponse countAll();

    TenderCountResponse countByContractor(Integer userId);

    Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Page<TenderResponse> findWithPagination(Integer currentPage, Integer tendersPerPage);

}