package pl.com.tenderflex.controller;

import static org.springframework.http.HttpStatus.*;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @PostMapping("/create")
    @ResponseStatus(OK)
    public void createTender(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @RequestBody TenderDetailsRequest tender) {
        tenderService.createTender(tender, contractorId);
    }

    @GetMapping("/amount_tenders_by_contractor")
    @ResponseStatus(OK)
    public Integer getAmountTendersByContractor(@AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return tenderService.getTendersAmountByContractor(contractorId);
    }

    @GetMapping("/tenders_by_contractor/{amount_tenders}/{amount_tenders_to_skip}")
    @ResponseStatus(OK)
    public List<ContractorTenderResponse> getAllByContractor(
            @PathVariable("amount_tenders") Optional<Integer> amountTenders,
            @PathVariable("amount_tenders_to_skip") Optional<Integer> amountTendersToSkip,
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return tenderService.getByContractor(contractorId, amountTenders.orElse(5), amountTendersToSkip.orElse(0));
    }

    @GetMapping("/all_tenders")
    @ResponseStatus(OK)
    public Page<BidderTenderResponse> getAllByCondition(@RequestParam(defaultValue = "1") Integer currentPage) {
        return tenderService.getByCondition(currentPage);
    }
}