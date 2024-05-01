package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.TypeOfTender;
import pl.com.tenderflex.payload.response.TypeOfTenderResponse;

@Mapper(componentModel = "spring")
public interface TypeOfTenderMapper {

    TypeOfTenderResponse typeOfTenderToTypeOfTenderResponse(TypeOfTender typeOfTender);
    
}