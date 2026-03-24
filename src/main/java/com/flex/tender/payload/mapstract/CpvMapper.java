package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;

import com.flex.tender.model.Cpv;
import com.flex.tender.payload.request.CpvRequest;
import com.flex.tender.payload.response.CpvResponse;

@Mapper(componentModel = "spring")
public interface CpvMapper {

    Cpv toEntity(CpvRequest cpvRequest);
    
    CpvResponse toResponse(Cpv cpv);
    
}