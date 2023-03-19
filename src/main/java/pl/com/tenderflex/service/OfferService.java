package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;

public interface OfferService {

    void createOffer(OfferDetailsRequest offer, Integer bidderId);

    OfferDetailsResponse getById(Integer offerId);

    Page<OfferResponse> getOffersByBidder(Integer bidderId, Integer currentPage);
    
    Page<OfferResponse> getOffersByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);

    Page<OfferResponse> getOffersByTender(Integer tenderId, Integer currentPage, Integer offersPerPage);

}