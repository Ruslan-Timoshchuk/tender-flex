package pl.com.tenderflex.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.AwardResponse;
import pl.com.tenderflex.payload.request.AwardRequest;
import pl.com.tenderflex.service.AwardService;

@RestController
@RequestMapping("/api/v1/awards")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;
    
    @Secured({ "CONTRACTOR" })
    @PostMapping
    public AwardResponse create(@RequestBody AwardRequest award) {
        return awardService.create(award);
    }
    
}