package com.flex.tender.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;

public interface TenderRepository {
    
    Tender save(Tender tender);

    Set<Tender> findWithPagination(Integer amountTenders, Integer amountTendersToSkip);
    
    Set<Tender> findByContractorWithPagination(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip);

    Integer countByContractor(Integer userId); 
    
    Integer countAll();

    Tender findById(Integer tenderId);

    void update(Tender tender);

    Set<Tender> findActiveWhereSubmissionIsExpired(ETenderStatus status, LocalDate currentDate);

    Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds);

}