package pl.com.tenderflex.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.OfferDetails;
import pl.com.tenderflex.payload.iresponse.response.DecisionResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferInListResponse;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.request.DecisionRequest;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;

public interface OfferService {

    void createOffer(OfferDetailsRequest offer, User bidder);

    OfferDetails getOfferDetails(Integer offerId, Collection<GrantedAuthority> authorities);

    Page<OfferInListResponse> getOffersByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferInListResponse> getOffersByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);

    void addAwardDecisionFile(AwardDecisionRequest award);

    void addRejectDecisionFile(RejectDecisionRequest reject);

    DecisionResponse saveApproveDecision(DecisionRequest decision);

    DecisionResponse saveDeclineDecision(DecisionRequest decision);

}