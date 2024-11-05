package pl.com.tenderflex.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.RejectResponse;
import pl.com.tenderflex.payload.request.RejectRequest;
import pl.com.tenderflex.service.RejectService;

@RestController
@RequestMapping("/api/v1/rejects")
@RequiredArgsConstructor
public class RejectController {

    private final RejectService rejectService;
    
    @Secured({ "CONTRACTOR" })
    @PostMapping
    public RejectResponse create(@RequestBody RejectRequest rejectRequest) {
        return rejectService.create(rejectRequest);
    }
    
}