package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.response.TotalResponse;

public interface TotalService {

    TotalResponse getTotalTendersAndOffersByContractor(Integer contractorId);
    
    TotalResponse getTotalTendersAndOffersByBidder(Integer bidderId);
    
}