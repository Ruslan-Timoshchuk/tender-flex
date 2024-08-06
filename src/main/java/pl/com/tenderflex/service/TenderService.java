package pl.com.tenderflex.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.TenderDetails;
import pl.com.tenderflex.payload.iresponse.response.TenderInListResponse;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, User contractor);
    
    Page<TenderInListResponse<Integer>> getContractorPage(Integer contractorId, Integer currentPage, Integer tendersPerPage);

    Page<TenderInListResponse<String>> getBidderPage(Integer bidderId, Integer currentPage, Integer tendersPerPage);

    TenderDetails getTenderDetails(Integer tenderId, Collection<GrantedAuthority> authorities);
    
}