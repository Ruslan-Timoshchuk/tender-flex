package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.request.ProcurementRequest;
import pl.com.tenderflex.payload.response.ProcurementResponse;

public interface ProcurementService {

    ProcurementResponse initiateProcurement(ProcurementRequest procurementRequest);

}