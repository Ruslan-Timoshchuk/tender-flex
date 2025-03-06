package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;

public interface OfferService {

    OfferResponse create(OfferRequest offer);

    OfferResponse findById(Integer offerId);

    Page<OfferResponse> getOffersByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> getOffersByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByBidder(Integer bidderId);
    
    OfferCountResponse countByContractor(Integer contractorId);

    OfferCountResponse countByTender(Integer tenderId);

    OfferStatusResponse checkOfferStatus(Integer userId, Integer tenderId);
    
}