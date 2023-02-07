package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.dto.OfferDetailsResponse;
import pl.com.tenderflex.dto.OfferResponse;
import pl.com.tenderflex.dto.OfferDetailsRequest;
import pl.com.tenderflex.service.OfferService;

@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(path = "/create", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOffer(@RequestPart("offer") OfferDetailsRequest offerDetailsRequest,
            @RequestPart MultipartFile document, @AuthenticationPrincipal(expression = "id") Integer bidderId) {
        offerService.createOffer(document, offerDetailsRequest, bidderId);
    }

    @GetMapping("/received_offers")
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> getAllByCondition(@AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return offerService.getOffersByContractor(contractorId);
    }

    @GetMapping("/details_for_contractor/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDetailsResponse getForContractorById(@PathVariable("id") Integer offerId) {
        return offerService.getById(offerId);
    }

    @GetMapping("/my_offers")
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> getAllByBidder(@AuthenticationPrincipal(expression = "id") Integer bidderId) {
        return offerService.getOffersByBidder(bidderId);
    }

    @GetMapping("/details_for_bidder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferDetailsResponse getForBidderById(@PathVariable("id") Integer offerId) {
        return offerService.getById(offerId);
    }
}