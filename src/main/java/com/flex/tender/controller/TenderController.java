package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.User;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.service.RoleBasedActionExecutor;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.tenders.path}")
public class TenderController {

    public static final String URI_TENDERS_PAGE = "/page";
    public static final String URI_TENDERS_ID = "/{id}";

    private final TenderService tenderService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_TENDERS_PAGE)
    public Page<TenderResponse> findPage(@AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> tenderService.findByContractorWithPagination(contractor.getId(), currentPage,
                        tendersPerPage),
                bidder -> tenderService.findByBidderWithPagination(bidder.getId(), currentPage, tendersPerPage));
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
        final var authorities = authentication.getAuthorities();
        return tenderService.countByUserAuthority(userId, authorities);
    }

}