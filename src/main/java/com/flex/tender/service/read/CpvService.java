package com.flex.tender.service.read;

import java.util.List;
import com.flex.tender.model.Cpv;
import com.flex.tender.payload.response.CpvResponse;

public interface CpvService {

    List<CpvResponse> findAll();

    Cpv findById(Integer cpvId);

}