package pl.com.tenderflex.controller;

import java.util.List;

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
import pl.com.tenderflex.dto.TenderDetails;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
public class TenderController {

    public static final int ITEMS_PER_PAGE = 5;
    
    private final TenderService tenderService;

    public TenderController(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @GetMapping("/tenders_by_contractor")
    @ResponseStatus(HttpStatus.OK)
    public List<Tender> getAllByContractor(@RequestParam(defaultValue = "5") Integer currentTendersAmount,
            @AuthenticationPrincipal(expression = "id") Integer contractorId) {
        int tendersToSkip = currentTendersAmount - ITEMS_PER_PAGE;
       return tenderService.getByContractor(contractorId, currentTendersAmount, tendersToSkip);
    }
    
    @PostMapping(consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTender(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @ModelAttribute Attachment attachment, @RequestPart("tender") TenderDetails tenderDetails) {
        tenderService.createTender(attachment, tenderDetails, contractorId);  
    }
}