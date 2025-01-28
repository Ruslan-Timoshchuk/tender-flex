package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.payload.request.CountryRequest;
import pl.com.tenderflex.payload.response.CountryResponse;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toEntity(CountryRequest countryRequest);
    
    CountryResponse toResponse(Country country);
    
}