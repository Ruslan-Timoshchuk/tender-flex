package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    public static final String URI_CONTRACTOR_TENDERS_PAGE = "/contractor-page";
    public static final String URI_BIDDER_TENDERS_PAGE = "/bidder-page";
    public static final String URI_TENDERS_ID = "/{id}";

    private final TenderService tenderService;

    @Secured(CONTRACTOR)
    @GetMapping(URI_CONTRACTOR_TENDERS_PAGE)
    public ResponseEntity<Page<ContractorTenderSummaryResponse>> findByContractorWithPagination(
            @AuthenticationPrincipal Integer contractorId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByContractorWithPagination(contractorId, currentPage, tendersPerPage));
    }
    
    @Secured(CONTRACTOR)
    @GetMapping(URI_BIDDER_TENDERS_PAGE)
    public ResponseEntity<Page<BidderTenderSummaryResponse>> findByBidderWithPagination(
            @AuthenticationPrincipal Integer contractorId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return ResponseEntity
                 .ok()
                 .body(tenderService.findByBidderWithPagination(contractorId, currentPage, tendersPerPage));
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_TENDERS_ID)
    public TenderResponse findDetailsById(@PathVariable("id") Integer tenderId) {
        return tenderService.findDetailsById(tenderId);
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping("/count")
    public TenderCountResponse count(Authentication authentication) {
        final var userId = (Integer) authentication.getPrincipal();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return tenderService.countByUserAuthority(userId, authorities);
    }

}