package pl.com.tenderflex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
public class TenderController {

    private final TenderService tenderService;
    
    public TenderController(TenderService tenderService) {
        this.tenderService = tenderService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTender(@AuthenticationPrincipal User contractor, @RequestBody Tender tender) {
        tender.setContractor(contractor);
        tenderService.createTender(tender);
    }
}