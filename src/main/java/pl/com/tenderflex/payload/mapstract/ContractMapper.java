package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.payload.iresponse.response.ContractResponse;
import pl.com.tenderflex.payload.request.ContractRequest;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "tender.id", source = "tenderId")
    @Mapping(target = "contractType.id", source = "contractTypeId")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "contractFile.id", source = "contractFileId")
    Contract contractRequestToContract(ContractRequest contractRequest);
    
    ContractResponse contractToContractResponse(Contract contract);

}