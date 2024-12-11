package pl.com.tenderflex.companyprofile.payload;

import org.mapstruct.Mapper;
import pl.com.tenderflex.companyprofile.model.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryResponse toResponse(Country country);
    
}