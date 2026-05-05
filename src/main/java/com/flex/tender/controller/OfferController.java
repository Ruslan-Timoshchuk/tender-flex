package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.offers.path}")
public class OfferController {

    public static final String URI_OFFERS_ID = "/{id}";
    public static final String URI_BIDDER_OFFERS_PAGE = "/bidder-page/{bidder-id}";
    public static final String URI_CONTRACTOR_OFFERS_PAGE = "/contractor-page/{contractor-id}";
    public static final String URI_OFFERS_PAGE_TENDER_ID = "/tender-page/{tender-id}";
    public static final String URI_COUNT_OFFERS = "/count";

    private final OfferService offerService;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_ID)
    public ResponseEntity<OfferDetailsResponse> findDetailsById(@PathVariable("id") Integer id) {
        return ResponseEntity
                   .ok(offerService.findDetailsById(id));
    }

    @Secured( BIDDER )
    @GetMapping(URI_BIDDER_OFFERS_PAGE)
    public ResponseEntity<Page<OfferSummaryResponse>> findByBidderWithPagination(
            @PathVariable("bidder-id") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByBidderWithPagination(bidderId, currentPage, offersPerPage));
    }
    
    @Secured( CONTRACTOR )
    @GetMapping(URI_CONTRACTOR_OFFERS_PAGE)
    public ResponseEntity<Page<OfferSummaryResponse>> findByContractorWithPagination(
            @PathVariable("contractor-id") Integer contractorId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByContractorWithPagination(contractorId, currentPage, offersPerPage));
    }
    
    @Secured( CONTRACTOR )
    @GetMapping(URI_OFFERS_PAGE_TENDER_ID)
    public ResponseEntity<Page<OfferSummaryResponse>> findByTenderWithPagination(
            @PathVariable("tender-id") Integer tenderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return ResponseEntity
                   .ok(offerService.findByTenderWithPagination(tenderId, currentPage, offersPerPage));
    }

    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping(URI_COUNT_OFFERS)
    public ResponseEntity<OfferCountResponse> count(Authentication authentication) {
        final var userId = (Integer) authentication.getPrincipal();
        final var authorities = authentication.getAuthorities();
        return ResponseEntity
                   .ok(offerService.countByUserAuthority(userId, authorities));
    }
    
}