package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.CPV;
import pl.com.tenderflex.payload.response.CPVresponse;

@Mapper(componentModel = "spring")
public interface CPVMapper {

    CPVresponse cpvToCPVResponce(CPV cpv);
    
}