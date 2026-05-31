package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.BidderTenderDetailsResponse;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.ContractorTenderDetailsResponse;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.tenders.path}")
public class TenderController {

    public static final String URL_CONTRACTOR_TENDERS_PAGE = "/contractor-page";
    public static final String URL_BIDDER_TENDERS_PAGE = "/bidder-page";
    public static final String URL_CONTRACTOR_TENDER_DETAILS_BY_ID = "contractor-details/{id}";
    public static final String URL_BIDDER_TENDER_DETAILS_BY_ID = "bidder-details/{id}";
    public static final String URL_CONTRACTOR_COUNT = "/contractor-count";
    public static final String URL_COUNT_ALL = "/count-all";

    private final TenderService tenderService;

    @Secured(CONTRACTOR)
    @PostMapping
    public ResponseEntity<ContractorTenderSummaryResponse> save(@AuthenticationPrincipal PrincipalSummary principal,
            @RequestBody TenderRequest tender) {
        return ResponseEntity.ok(tenderService.save(principal, tender));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_TENDERS_PAGE)
    public ResponseEntity<SummaryPage<ContractorTenderSummaryResponse>> findByContractorWithPagination(
            @AuthenticationPrincipal(expression = "userId") Integer contractorId, 
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByContractorWithPagination(contractorId, page, pageSize));
    }
    
    @Secured(BIDDER)
    @GetMapping(URL_BIDDER_TENDERS_PAGE)
    public ResponseEntity<SummaryPage<BidderTenderSummaryResponse>> findByBidderWithPagination(
            @AuthenticationPrincipal(expression = "userId") Integer bidderId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByBidderWithPagination(bidderId, currentPage, tendersPerPage));
    }

    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_TENDER_DETAILS_BY_ID)
    public ResponseEntity<ContractorTenderDetailsResponse> loadContractorTenderDetailsById(
            @PathVariable("id") Integer tenderId) {
        return ResponseEntity.ok(tenderService.loadContractortTenderDetailsById(tenderId));
    }

    @Secured(BIDDER)
    @GetMapping(URL_BIDDER_TENDER_DETAILS_BY_ID)
    public ResponseEntity<BidderTenderDetailsResponse> loadBidderTenderDetailsById(
            @PathVariable("id") Integer tenderId) {
        return ResponseEntity.ok(tenderService.loadBidderTenderDetailsById(tenderId));
    }

    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_COUNT)
    public ResponseEntity<TenderCountResponse> countByContractor(
            @AuthenticationPrincipal(expression = "userId") Integer contractorId) {
        return ResponseEntity
                   .ok(tenderService.countByContractor(contractorId));
    }
    
    @Secured(BIDDER)
    @GetMapping(URL_COUNT_ALL)
    public ResponseEntity<TenderCountResponse> countAll() {
        return ResponseEntity
                   .ok(tenderService.countAll());
    }

}