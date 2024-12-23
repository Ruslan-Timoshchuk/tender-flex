package pl.com.tenderflex.contract.payload;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.contract.model.Contract;
import pl.com.tenderflex.currency.payload.CurrencyMapper;
import pl.com.tenderflex.payload.mapstract.FileMetadataMapper;

@Mapper(componentModel = "spring", uses = { ContractTypeMapper.class, CurrencyMapper.class, FileMetadataMapper.class })
public interface ContractMapper {

    @Mapping(target = "contractType.id", source = "contractTypeId")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "fileMetadata.id", source = "fileId")
    Contract toEntity(ContractRequest contractRequest);

    @Mapping(target = "signedDeadline", source = "signedDeadline", dateFormat = "dd/MM/yyyy")
    ContractResponse toResponse(Contract contract);

}