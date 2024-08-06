package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.payload.iresponse.response.CpvResponse;

public interface CpvService {

    List<CpvResponse> getAllCPVs();

}
