package com.flex.tender.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.User;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferStatusResponse;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.RoleBasedActionExecutor;
import lombok.RequiredArgsConstructor;
import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.offers.path}")
public class OfferController {

    public static final String URI_OFFERS_ID = "/api/v1/offers/{id}";
    public static final String URI_OFFERS_PAGE = "/api/v1/offers/page";
    public static final String URI_OFFERS_PAGE_TENDER_ID = "/api/v1/offers/page/{tender-id}";

    public static final String URI_OFFERS_COUNT_TENDER = "/api/v1/offers/count/{tender-id}";
    public static final String URI_OFFERS_STATUS_BIDDER_ID_TENDER_ID = "/api/v1/offers/status/{user-id}/{tender-id}";
    public static final String URL_OFFERS_SELECT_OFFER = "/api/v1/offers/winning-offer";
    public static final String URL_OFFERS_SIGN_CONTRACT = "/api/v1/offers/sign-contact";

    private final OfferService offerService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_ID)
    public OfferResponse findDetailsById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.findDetailsByContractor(id), bidder -> offerService.findDetailsByBidder(id));
    }

    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping(URI_OFFERS_PAGE)
    public Page<OfferResponse> findPage(@AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.findPageByContractor(user.getId(), currentPage, offersPerPage),
                bidder -> offerService.findPageByBidder(user.getId(), currentPage, offersPerPage));
    }
    
    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping(URI_OFFERS_PAGE_TENDER_ID)
    public Page<OfferResponse> findPage(@PathVariable("tender-id") Integer tenderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.findPageByTender(tenderId, currentPage, offersPerPage);
    }

    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping("/count")
    public OfferCountResponse count(Authentication authentication) {
        final var userId = (Integer) authentication.getPrincipal();
        final var authorities = authentication.getAuthorities();
        return offerService.countByUserAuthority(userId, authorities);
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
        return offerService.checkOfferStatus(tenderId, userId);
    }
    
}