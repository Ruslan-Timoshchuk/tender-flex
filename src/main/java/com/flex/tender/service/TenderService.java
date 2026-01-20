package com.flex.tender.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;

public interface TenderService {

    Tender save(Tender tender);

    Tender findById(Integer id);

    TenderResponse findDetailsById(Integer id);

    TenderCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities);

    Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Page<TenderResponse> findByBidderWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Tender close(Tender tender);

    Tender closeIfHasNoPendingOffers(Tender tender);

    void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate);

}