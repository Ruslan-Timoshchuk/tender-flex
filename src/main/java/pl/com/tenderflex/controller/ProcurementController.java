package pl.com.tenderflex.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.request.ProcurementRequest;
import pl.com.tenderflex.payload.response.ProcurementResponse;
import pl.com.tenderflex.service.ProcurementService;

@RestController
@RequiredArgsConstructor
public class ProcurementController {

    public static final String URI_PROCUREMENTS = "/api/v1/procurements";
  
    private final ProcurementService procurementService;
    
    @Secured(CONTRACTOR)
    @PostMapping(value = URI_PROCUREMENTS, consumes = APPLICATION_JSON_VALUE)
    public ProcurementResponse initiateProcurement(@RequestBody ProcurementRequest procurementRequest) {
         return procurementService.initiateProcurement(procurementRequest);
    }
   
}