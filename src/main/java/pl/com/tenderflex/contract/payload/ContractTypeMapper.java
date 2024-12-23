package pl.com.tenderflex.contract.payload;

import org.mapstruct.Mapper;

import pl.com.tenderflex.contract.model.ContractType;

@Mapper(componentModel = "spring")
public interface ContractTypeMapper {

    ContractTypeResponse teResponse(ContractType typeOfTender);
    
}