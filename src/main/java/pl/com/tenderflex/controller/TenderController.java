package pl.com.tenderflex.controller;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.TenderDetails;
import pl.com.tenderflex.payload.iresponse.response.TenderDetailsContractorResponse;
import pl.com.tenderflex.payload.iresponse.response.TenderInListResponse;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @Secured("CONTRACTOR")
    @PostMapping
    public void createTender(@AuthenticationPrincipal User contractor,
            @RequestBody TenderDetailsRequest tender) {
        tenderService.createTender(tender, contractor);
    }

    @Secured("CONTRACTOR")
    @GetMapping("/list/contractor")
    public Page<TenderInListResponse<Integer>> getAllByContractor(@RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage,
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return tenderService.getContractorPage(contractorId, currentPage, tendersPerPage);
    }

    @Secured("BIDDER")
    @GetMapping("/list/bidder")
    public Page<TenderInListResponse<String>> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return tenderService.getBidderPage(bidderId, currentPage, tendersPerPage);
    }

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/details/{id}")
    public TenderDetails getTenderDetailsById(
            @AuthenticationPrincipal(expression = "authorities") Collection<GrantedAuthority> authorities,
            @PathVariable("id") Integer tenderId) {
        return tenderService.getTenderDetails(tenderId, authorities);
    }
}