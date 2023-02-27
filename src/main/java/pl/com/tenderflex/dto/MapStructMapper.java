package pl.com.tenderflex.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Country;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "countryName", source = "countryName")
    CountryResponse countryToCountryResponse(Country country);

}