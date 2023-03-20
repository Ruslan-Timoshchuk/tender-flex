package pl.com.tenderflex.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.TotalResponse;
import pl.com.tenderflex.service.TotalService;

@RestController
@RequestMapping("/api/v1/total")
@RequiredArgsConstructor
public class TotalController {

    private final TotalService totalService;
 
    @Secured("CONTRACTOR")
    @GetMapping("/contractor")
    public TotalResponse getAmountTendersAndOffersByContractor(
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return totalService.getTotalTendersAndOffersByContractor(contractorId);
    }
    
    @Secured("BIDDER")
    @GetMapping("/bidder")
    public TotalResponse getAmountTendersAndOffersByBidder(
            @AuthenticationPrincipal(expression = "id") Integer bidderId) {
        return totalService.getTotalTendersAndOffersByBidder(bidderId);
    }
}