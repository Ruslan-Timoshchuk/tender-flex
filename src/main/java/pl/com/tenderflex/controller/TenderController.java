package pl.com.tenderflex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.BidderTenderResponse;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.dto.ContractorTenderResponse;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
public class TenderController {

    private final TenderService tenderService;

    public TenderController(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @PostMapping(path = "/create", consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTender(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @ModelAttribute Attachment attachment, @RequestPart("tender") TenderDetailsRequest tenderDetails) {
        tenderService.createTender(attachment, tenderDetails, contractorId);
    }

    @GetMapping("/tenders_by_contractor")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContractorTenderResponse> getAllByContractor(@RequestParam(defaultValue = "1") Integer currentPage,
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        return tenderService.getByContractor(contractorId, currentPage);
    }

    @GetMapping("/all_tenders")
    @ResponseStatus(HttpStatus.OK)
    public Page<BidderTenderResponse> getAllByCondition(@RequestParam(defaultValue = "1") Integer currentPage) {
        return tenderService.getByCondition(currentPage);
    }
}