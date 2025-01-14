package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Cpv;
import pl.com.tenderflex.payload.response.CpvResponse;

@Mapper(componentModel = "spring")
public interface CpvMapper {

    CpvResponse toResponse(Cpv cpv);
    
}