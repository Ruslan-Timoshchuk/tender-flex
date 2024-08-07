package pl.com.tenderflex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import java.util.Collection;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.OfferDetails;
import pl.com.tenderflex.payload.iresponse.response.DecisionResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferInListResponse;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.request.DecisionRequest;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;
import pl.com.tenderflex.service.OfferService;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @Secured("BIDDER")
    @PostMapping
    public void createOffer(@AuthenticationPrincipal User bidder,
            @RequestBody OfferDetailsRequest offer) {
        offerService.createOffer(offer, bidder);
    }

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/details/{id}")
    public OfferDetails getOfferDetailsById(
            @AuthenticationPrincipal(expression = "authorities") Collection<GrantedAuthority> authorities,
            @PathVariable("id") Integer offerId) {
        return offerService.getOfferDetails(offerId, authorities);
    }

    @Secured("BIDDER")
    @GetMapping("/list/bidder")
    public Page<OfferInListResponse> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByBidder(bidderId, currentPage, offersPerPage);
    }

    @Secured("CONTRACTOR")
    @GetMapping("/list/contractor")
    public Page<OfferInListResponse> getAllByContractor(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByContractor(contractorId, currentPage, offersPerPage);
    }

    @Secured("CONTRACTOR")
    @PostMapping("/decision/award")
    public void saveAwardDecisionDocumentForOffer(@RequestBody AwardDecisionRequest award) {
        offerService.addAwardDecisionFile(award);
    }

    @Secured("CONTRACTOR")
    @PostMapping("/decision/reject")
    public void saveRejectDecisionDocumentForOffer(@RequestBody RejectDecisionRequest reject) {
        offerService.addRejectDecisionFile(reject);
    }

    @Secured("BIDDER")
    @PostMapping("/decision/approve")
    public DecisionResponse saveApproveDecisionStatusForOffer(@RequestBody DecisionRequest decision) {
        return offerService.saveApproveDecision(decision);
    }

    @Secured("BIDDER")
    @PostMapping("/decision/decline")
    public DecisionResponse saveDeclineDecisionStatusForOffer(@RequestBody DecisionRequest decision) {
        return offerService.saveDeclineDecision(decision);
    }
}