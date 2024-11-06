package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.payload.iresponse.response.BidCountResponse;

@Mapper(componentModel = "spring")
public interface BidCountMapper {

    @Mapping(target = "bidCount", source = "bidCount")
    BidCountResponse bidCountToBidCountResponse(Integer bidCount);
    
}