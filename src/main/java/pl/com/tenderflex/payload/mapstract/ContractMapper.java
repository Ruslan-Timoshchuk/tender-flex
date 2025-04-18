package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.payload.request.ContractRequest;
import pl.com.tenderflex.payload.response.ContractResponse;

@Mapper(componentModel = "spring", uses = { ContractTypeMapper.class, CurrencyMapper.class, FileMetadataMapper.class })
public interface ContractMapper {

    Contract toEntity(ContractRequest contractRequest);

    @Mapping(target = "signedDeadline", source = "contract.signedDeadline", dateFormat = "dd/MM/yyyy")
    ContractResponse toResponse(Contract contract, Boolean hasOffer);

}