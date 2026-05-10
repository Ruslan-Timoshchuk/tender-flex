package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.tenders.path}")
public class TenderController {

    public static final String URL_CONTRACTOR_TENDERS_PAGE = "/contractor-page";
    public static final String URL_BIDDER_TENDERS_PAGE = "/bidder-page";
    public static final String URL_TENDER_ID = "/{id}";
    public static final String URL_CONTRACTOR_COUNT = "/contractor-count/{contractor-id}";
    public static final String URL_COUNT_ALL = "/count-all";

    private final TenderService tenderService;

    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_TENDERS_PAGE)
    public ResponseEntity<Page<ContractorTenderSummaryResponse>> findByContractorWithPagination(
            @AuthenticationPrincipal Integer contractorId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByContractorWithPagination(contractorId, currentPage, tendersPerPage));
    }
    
    @Secured(BIDDER)
    @GetMapping(URL_BIDDER_TENDERS_PAGE)
    public ResponseEntity<Page<BidderTenderSummaryResponse>> findByBidderWithPagination(
            @AuthenticationPrincipal Integer contractorId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByBidderWithPagination(contractorId, currentPage, tendersPerPage));
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_TENDER_ID)
    public TenderResponse findDetailsById(@PathVariable("id") Integer tenderId) {
        return tenderService.findDetailsById(tenderId);
    }

    @Secured(CONTRACTOR)
    @GetMapping(URL_CONTRACTOR_COUNT)
    public TenderCountResponse countByContractor(@PathVariable("contractor-id") Integer contractorId) {
        return tenderService.countByContractor(contractorId);
    }
    
    @Secured(BIDDER)
    @GetMapping(URL_COUNT_ALL)
    public TenderCountResponse countAll() {
        return tenderService.countAll();
    }

}