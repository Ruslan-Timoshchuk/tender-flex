package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.payload.iresponse.response.CPVresponse;

public interface CpvService {

    List<CPVresponse> getAllCPVs();

}
