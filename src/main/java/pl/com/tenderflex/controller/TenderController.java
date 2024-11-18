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
import pl.com.tenderflex.payload.iresponse.response.TenderResponse;
import pl.com.tenderflex.payload.iresponse.response.BidCountResponse;
import pl.com.tenderflex.payload.iresponse.response.TenderListResponse;
import pl.com.tenderflex.payload.request.TenderRequest;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.TenderService;
import pl.com.tenderflex.service.impl.RoleBasedActionExecutorImpl;

@RestController
@RequestMapping("/api/v1/tenders")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;
    private final RoleBasedActionExecutorImpl roleBasedActionExecutor;
    
    @Secured("CONTRACTOR")
    @PostMapping
    public TenderResponse createTender(@RequestBody TenderRequest tenderRequest) {
         return tenderService.create(tenderRequest);
    }

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/{user-id}")
    public Page<TenderListResponse> getTendersPage(
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage,
            @PathVariable("user-id") Integer userId) {
        return tenderService.getTendersPage(userId, currentPage, tendersPerPage);
    }
    
    @Secured("CONTRACTOR")
    @GetMapping("/contractors/{contractor-id}")
    public Page<TenderListResponse> getPageByContractor(@RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage,
            @PathVariable("contractor-id") Integer contractorId) {
        return tenderService.getTendersPageByContractor(contractorId, currentPage, tendersPerPage);
    }

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/details/{id}")
    public TenderResponse getTenderDetailsById(
            @AuthenticationPrincipal(expression = "authorities") Collection<GrantedAuthority> authorities,
            @PathVariable("id") Integer tenderId) {
        return tenderService.getTenderDetails(tenderId, authorities);
    }
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/count")
    public BidCountResponse getTendersCount(@AuthenticationPrincipal User user) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> tenderService.countByContractor(contractor.getId()), 
                bidder -> tenderService.countAll());    
    }

}