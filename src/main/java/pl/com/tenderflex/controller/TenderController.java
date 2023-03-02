package pl.com.tenderflex.controller;

import static org.springframework.http.HttpStatus.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dto.TenderDetailsRequest;
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
}