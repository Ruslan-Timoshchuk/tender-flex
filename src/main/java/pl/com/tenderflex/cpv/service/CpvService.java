package pl.com.tenderflex.cpv.service;

import java.util.List;

import pl.com.tenderflex.cpv.payload.CpvResponse;

public interface CpvService {

    List<CpvResponse> getAllCpvs();

    CpvResponse getById(Integer id);

}