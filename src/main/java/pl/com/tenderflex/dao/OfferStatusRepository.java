package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.OfferStatus;

public interface OfferStatusRepository {

    OfferStatus getByTenderAndBidder(Integer tenderId, Integer bidderId);

}