package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.ContractType;
import pl.com.tenderflex.payload.iresponse.response.ContractTypeResponse;

@Mapper(componentModel = "spring")
public interface ContractTypeMapper {

    ContractTypeResponse typeOfTenderToTypeOfTenderResponse(ContractType typeOfTender);
    
}