package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.response.TenderCountResponse;
import pl.com.tenderflex.payload.response.TenderResponse;

public interface TenderService {

    Tender save(Tender tender);

    TenderResponse findById(Integer tenderId);

    TenderCountResponse countAll();

    TenderCountResponse countByContractor(Integer userId);

    Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Page<TenderResponse> findByBidderWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    void closeTheTender(Tender tender);

}