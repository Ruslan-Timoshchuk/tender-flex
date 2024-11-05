package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Cpv;
import pl.com.tenderflex.payload.iresponse.response.CpvResponse;

@Mapper(componentModel = "spring")
public interface CpvMapper {

    CpvResponse cpvToCpvResponce(Cpv cpv);
    
}