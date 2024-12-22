package pl.com.tenderflex.companyprofile.payload;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.companyprofile.model.CompanyProfile;

@Mapper(componentModel = "spring", uses = { CountryMapper.class, ContactPersonMapper.class })
public interface CompanyProfileMapper {

    @Mapping(target = "officialName", source = "officialName")
    @Mapping(target = "registrationNumber", source = "registrationNumber")
    @Mapping(target = "country.id", source = "countryId")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "contactPerson.firstName", source = "firstName")
    @Mapping(target = "contactPerson.lastName", source = "lastName")
    @Mapping(target = "contactPerson.phoneNumber", source = "phoneNumber")
    CompanyProfile toEntity(CompanyProfileRequest companyProfile);

    CompanyProfileResponse toResponse(CompanyProfile companyProfile);

}