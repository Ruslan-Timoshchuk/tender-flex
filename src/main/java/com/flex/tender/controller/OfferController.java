package com.flex.tender.controller;

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
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;
import com.flex.tender.service.OfferService;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.offers.path}")
public class OfferController {

    public static final String URL_OFFERS_ID = "/{id}";
    public static final String URL_BIDDER_OFFERS_PAGE = "/bidder-page/{bidder-id}";
    public static final String URL_CONTRACTOR_OFFERS_PAGE = "/contractor-page/{contractor-id}";
    public static final String URL_TENDER_OFFERS_PAGE = "/tender-page/{tender-id}";
    public static final String URL_BIDDER_COUNT = "/bidder-count";
    public static final String URL_CONTRACTOR_COUNT = "/contractor-count";

    private final OfferService offerService;

    @Secured(BIDDER)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferSummaryResponse> save(@AuthenticationPrincipal PrincipalSummary principalSummary,
            @RequestBody OfferRequest offerRequest) {
       return ResponseEntity
                .ok(offerService.save(principalSummary, offerRequest));
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_OFFERS_ID)
    public ResponseEntity<OfferDetailsResponse> findDetailsById(@PathVariable("id") Integer id) {
        return ResponseEntity
                   .ok(offerService.findDetailsById(id));
    }

    @Secured( BIDDER )
    @GetMapping(URL_BIDDER_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<OfferSummaryResponse>> findByBidderWithPagination(
            @PathVariable("bidder-id") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByBidderWithPagination(bidderId, currentPage, offersPerPage));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<OfferSummaryResponse>> findByContractorWithPagination(
            @PathVariable("contractor-id") Integer contractorId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByContractorWithPagination(contractorId, currentPage, offersPerPage));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_TENDER_OFFERS_PAGE)
    public ResponseEntity<SummaryPage<TenderOfferSummaryResponse>> findByTenderWithPagination(
            @PathVariable("tender-id") Integer tenderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByTenderWithPagination(tenderId, currentPage, offersPerPage));
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