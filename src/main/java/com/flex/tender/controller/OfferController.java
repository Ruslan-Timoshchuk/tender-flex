package com.flex.tender.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;
import com.flex.tender.service.facade.OfferManager;
import com.flex.tender.service.read.OfferDetailsService;
import com.flex.tender.service.write.OfferService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.offers.path}")
public class OfferController {

    public static final String URL_OFFERS_ID = "/{id}";
    public static final String URL_CONTRACTOR_OFFER_DETAILS = "contractor-details/{offer-id}";
    public static final String URL_BIDDER_OFFERS_PAGE = "/bidder-page";
    public static final String URL_CONTRACTOR_OFFERS_PAGE = "/contractor-page";
    public static final String URL_TENDER_OFFERS_PAGE = "/tender-page/{tender-id}";
    public static final String URL_BIDDER_COUNT = "/bidder-count";
    public static final String URL_CONTRACTOR_COUNT = "/contractor-count";

    private final OfferManager offerManager;
    private final OfferService offerService;
    private final OfferDetailsService offerDetailsService;

    @Secured(BIDDER)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferSummaryResponse> save(@AuthenticationPrincipal PrincipalSummary principalSummary,
            @RequestBody OfferRequest offerRequest) {
       return ResponseEntity
                .ok(offerManager.save(principalSummary, offerRequest));
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_OFFERS_ID)
    public ResponseEntity<OfferDetailsResponse> findDetailsById(@PathVariable("id") Integer id) {
        return ResponseEntity
                   .ok(offerService.findDetailsById(id));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_OFFER_DETAILS)
    public ResponseEntity<ContractorOfferDetailsResponse> findContractorDetailsById(@PathVariable("offer-id") Integer offerId) {
        return ResponseEntity
                   .ok(offerDetailsService.findContractorOfferDetailsById(offerId));
    }

    @Secured(BIDDER)
    @GetMapping(URL_BIDDER_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<OfferSummaryResponse>> findByBidderWithPagination(
            @AuthenticationPrincipal(expression = "userId") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer requestedPage,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                   .ok(offerDetailsService.findByBidderWithPagination(bidderId, requestedPage, pageSize));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<OfferSummaryResponse>> findByContractorWithPagination(
            @AuthenticationPrincipal(expression = "userId") Integer contractorId,
            @RequestParam(defaultValue = "0") Integer requestedPage,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                   .ok(offerDetailsService.findByContractorWithPagination(contractorId, requestedPage, pageSize));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_TENDER_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<TenderOfferSummaryResponse>> findByTenderWithPagination(
            @PathVariable("tender-id") Integer tenderId,
            @RequestParam(defaultValue = "0") Integer requestedPage,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                   .ok(offerDetailsService.findByTenderWithPagination(tenderId, requestedPage, pageSize));
    }

    @Secured(BIDDER)
    @GetMapping(URL_BIDDER_COUNT)
    public ResponseEntity<OfferCountResponse> countByBidder(
            @AuthenticationPrincipal(expression = "userId") Integer bidderId) {
        return ResponseEntity
                   .ok(offerService.countByBidder(bidderId));
    }

    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_COUNT)
    public ResponseEntity<OfferCountResponse> countByContractor(
            @AuthenticationPrincipal(expression = "userId") Integer contractorId) {
        return ResponseEntity
                   .ok(offerService.countByContractor(contractorId));
    }
    
}