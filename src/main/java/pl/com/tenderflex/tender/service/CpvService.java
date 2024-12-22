package pl.com.tenderflex.tender.service;

import java.util.List;

import pl.com.tenderflex.tender.payload.CpvResponse;

public interface CpvService {

    List<CpvResponse> getAllCpvs();

    CpvResponse getById(Integer id);

}