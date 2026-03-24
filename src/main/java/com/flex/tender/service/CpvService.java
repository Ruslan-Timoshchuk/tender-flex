package com.flex.tender.service;

import java.util.List;

import com.flex.tender.payload.response.CpvResponse;

public interface CpvService {

    List<CpvResponse> getAllCpvs();

}