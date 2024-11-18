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
import pl.com.tenderflex.payload.iresponse.response.BidCountResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferInListResponse;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.RoleBasedActionExecutor;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;

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

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/count")
    public BidCountResponse getOffersCount(@AuthenticationPrincipal User user) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.countByContractor(contractor.getId()),
                bidder -> offerService.countByBidder(bidder.getId()));
    }
    
}