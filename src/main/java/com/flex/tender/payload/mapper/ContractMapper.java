package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flex.tender.model.Contract;
import com.flex.tender.payload.request.ContractRequest;
import com.flex.tender.payload.response.ContractResponse;

@Mapper(componentModel = "spring", uses = { ContractTypeMapper.class, CurrencyMapper.class, FileMetadataMapper.class })
public interface ContractMapper {

    Contract toEntity(ContractRequest contractRequest);

    @Mapping(target = "status", source = "globalStatus")
    @Mapping(target = "signedDeadline", source = "contract.signedDeadline", dateFormat = "dd/MM/yyyy")
    ContractResponse toResponse(Contract contract);

}