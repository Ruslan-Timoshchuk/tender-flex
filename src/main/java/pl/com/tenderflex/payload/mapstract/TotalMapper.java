package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Total;
import pl.com.tenderflex.payload.iresponse.response.TotalResponse;

@Mapper(componentModel = "spring")
public interface TotalMapper {

    TotalResponse totalToTotalResponse(Total total);
    
}