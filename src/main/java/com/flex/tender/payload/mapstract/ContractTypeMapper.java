package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;

import com.flex.tender.model.ContractType;
import com.flex.tender.payload.request.ContractTypeRequest;
import com.flex.tender.payload.response.ContractTypeResponse;

@Mapper(componentModel = "spring")
public interface ContractTypeMapper {

    ContractType toEntity(ContractTypeRequest contractTypeRequest);
    
    ContractTypeResponse toResponse(ContractType typeOfTender);
    
}