package pl.com.tenderflex.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.response.TenderResponse;
import pl.com.tenderflex.payload.iresponse.response.BidCountResponse;
import pl.com.tenderflex.payload.iresponse.response.TenderListResponse;
import pl.com.tenderflex.payload.request.TenderRequest;

public interface TenderService {

    TenderResponse create(TenderRequest tender);

    Page<TenderListResponse> getTendersPage(Integer userId, Integer currentPage, Integer tendersPerPage);
    
    Page<TenderListResponse> getTendersPageByContractor(Integer contractorId, Integer currentPage,
            Integer tendersPerPage);

    TenderResponse getTenderDetails(Integer tenderId, Collection<GrantedAuthority> authorities);

    BidCountResponse countAll();
    
    BidCountResponse countByContractor(Integer userId);

}