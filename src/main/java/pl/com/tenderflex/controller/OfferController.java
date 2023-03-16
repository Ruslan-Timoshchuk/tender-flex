package pl.com.tenderflex.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
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
}