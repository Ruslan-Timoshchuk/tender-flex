package pl.com.tenderflex.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @Secured("CONTRACTOR")
    @PostMapping
    public void createTender(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @RequestBody TenderDetailsRequest tender) {
        tenderService.createTender(tender, contractorId);
    }

    @Secured("CONTRACTOR")
    @GetMapping("/list/contractor")
    public Page<ContractorTenderResponse> getAllByContractor(@RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage,
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return tenderService.getByContractor(contractorId, currentPage, tendersPerPage);
    }

    @Secured("BIDDER")
    @GetMapping("/list/bidder")
    public Page<BidderTenderResponse> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId, 
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return tenderService.getByBidder(bidderId, currentPage, tendersPerPage);
    }

    @Secured("CONTRACTOR")
    @GetMapping("/details/contractor/{id}")
    public ContractorTenderDetailsResponse getTenderByIdForContractor(@PathVariable("id") Integer tenderId) {
        return tenderService.getByIdForContractor(tenderId);
    }
    
    @Secured("BIDDER")
    @GetMapping("/details/offer/{id}")
    public BidderTenderDetailsResponse getTenderByOfferId(@PathVariable("id") Integer offerId) {
        return tenderService.getTenderByOfferId(offerId);
    }
     
    @Secured("BIDDER")
    @GetMapping("/details/bidder/{id}")
    public BidderTenderDetailsResponse getTenderByIdForBidder(@PathVariable("id") Integer tenderId) {
        return tenderService.getByIdForBidder(tenderId);
    }
}