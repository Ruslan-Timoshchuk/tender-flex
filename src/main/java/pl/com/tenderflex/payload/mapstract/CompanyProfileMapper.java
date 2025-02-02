package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.payload.request.CompanyProfileRequest;
import pl.com.tenderflex.payload.response.CompanyProfileResponse;

@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface CompanyProfileMapper {

    CompanyProfile toEntity(CompanyProfileRequest companyProfile);

    CompanyProfileResponse toResponse(CompanyProfile companyProfile);

}