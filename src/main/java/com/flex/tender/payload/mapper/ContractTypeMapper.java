package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import com.flex.tender.model.ContractType;
import com.flex.tender.payload.response.ContractTypeResponse;

@Mapper(componentModel = "spring")
public interface ContractTypeMapper {
    
    ContractTypeResponse toResponse(ContractType typeOfTender);
    
}