package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;

public interface OfferService {

    OfferResponse create(OfferRequest offer);

    OfferResponse findById(Integer offerId);

    Page<OfferResponse> findPageByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByTender(Integer tenderId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByBidder(Integer bidderId);
    
    OfferCountResponse countByContractor(Integer contractorId);

    OfferCountResponse countByTender(Integer tenderId);

    OfferStatusResponse checkOfferStatus(Integer userId, Integer tenderId);

    Offer selectWinningOffer(Integer offerId, Integer awardId);
    
}