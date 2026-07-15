package com.flex.tender.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;

public interface TenderRepository {
    
    Tender save(Tender tender);

    List<Tender> findWithPagination(Integer limit, Integer offset);
    
    List<Tender> findByContractorWithPagination(Integer contractorId, Integer limit, Integer offset);

    Integer countByContractor(Integer userId); 
    
    Integer countAll();

    Tender findById(Integer tenderId);

    void update(Tender tender);

    List<Tender> findActiveWhereSubmissionIsExpired(ETenderStatus status, LocalDate date);

    Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds);

}