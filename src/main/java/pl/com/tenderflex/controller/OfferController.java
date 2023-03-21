package pl.com.tenderflex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.service.OfferService;

@RestController
@RequestMapping("/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @Secured("BIDDER")
    @PostMapping
    public void createOffer(@AuthenticationPrincipal(expression = "id") Integer bidderId,
            @RequestBody OfferDetailsRequest offer) {
        offerService.createOffer(offer, bidderId);
    }

    @Secured({ "BIDDER", "CONTRACTOR" })
    @GetMapping("/details/{id}")
    public OfferDetailsResponse getOfferDetailsById(@PathVariable("id") Integer offerId) {
        return offerService.getById(offerId);
    }

    @Secured("BIDDER")
    @GetMapping("/list/bidder")
    public Page<OfferResponse> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer currentPage) {
        return offerService.getOffersByBidder(bidderId, currentPage);
    }

    @Secured("CONTRACTOR")
    @GetMapping("/list/contractor")
    public Page<OfferResponse> getAllByContractor(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByContractor(contractorId, currentPage, offersPerPage);
    }
    
    @Secured("CONTRACTOR")
    @GetMapping("/list/{tender_id}")
    public Page<OfferResponse> getAllByTender(@PathVariable("tender_id") Integer tenderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByTender(tenderId, currentPage, offersPerPage);
    }
}