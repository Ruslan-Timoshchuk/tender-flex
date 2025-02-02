package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;

import pl.com.tenderflex.model.ContractType;
import pl.com.tenderflex.payload.request.ContractTypeRequest;
import pl.com.tenderflex.payload.response.ContractTypeResponse;

@Mapper(componentModel = "spring")
public interface ContractTypeMapper {

    ContractType toEntity(ContractTypeRequest contractTypeRequest);
    
    ContractTypeResponse toResponse(ContractType typeOfTender);
    
}