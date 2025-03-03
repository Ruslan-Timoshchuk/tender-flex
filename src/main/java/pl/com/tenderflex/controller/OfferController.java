package pl.com.tenderflex.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.RoleBasedActionExecutor;

@RestController
@RequiredArgsConstructor
public class OfferController {

    public static final String URI_OFFERS = "/api/offers";
    public static final String URI_OFFERS_ID = "/api/offers/{id}";
    public static final String URI_OFFERS_BIDDER_PAGE = "/api/offers/bidder/page";
    public static final String URI_OFFERS_CONTRACTOR_PAGE = "/api/offers/contractor/page";
    public static final String URI_OFFERS_PAGE = "/api/offers/page";
    public static final String URI_OFFERS_COUNT_USER = "/api/offers/count";
    public static final String URI_OFFERS_COUNT_TENDER = "/api/offers/count/{tender-id}";
    public static final String URI_OFFERS_STATUS_BIDDER_ID_TENDER_ID = "/api/offers/status/{user-id}/{tender-id}";

    private final OfferService offerService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;

    @Secured(BIDDER)
    @PostMapping(value = URI_OFFERS, consumes = APPLICATION_JSON_VALUE)
    public void create(@RequestBody OfferRequest offer) {
        offerService.create(offer);
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_ID)
    public OfferResponse findById(@PathVariable("id") Integer id) {
        return offerService.findById(id);
    }

    @Secured(BIDDER)
    @GetMapping(URI_OFFERS_BIDDER_PAGE)
    public Page<OfferResponse> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByBidder(bidderId, currentPage, offersPerPage);
    }

    @Secured(CONTRACTOR)
    @GetMapping(URI_OFFERS_CONTRACTOR_PAGE)
    public Page<OfferResponse> getAllByContractor(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.getOffersByContractor(contractorId, currentPage, offersPerPage);
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_COUNT_USER)
    public OfferCountResponse countByUser(@AuthenticationPrincipal User user) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.countByContractor(contractor.getId()),
                bidder -> offerService.countByBidder(bidder.getId()));
    }

    @Secured(CONTRACTOR)
    @GetMapping(URI_OFFERS_COUNT_TENDER)
    public OfferCountResponse countByTender(@PathVariable("tender-id") Integer tenderId) {
        return offerService.countByTender(tenderId);
    }

    @Secured(BIDDER)
    @GetMapping(URI_OFFERS_STATUS_BIDDER_ID_TENDER_ID)
    public OfferStatusResponse checkOfferStatus(@PathVariable("user-id") Integer userId,
            @PathVariable("tender-id") Integer tenderId) {
        return offerService.checkOfferStatus(userId, tenderId);
    }

}