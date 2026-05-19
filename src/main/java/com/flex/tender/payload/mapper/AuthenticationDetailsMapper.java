package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.User;
import com.flex.tender.model.embedded.PrincipalDetails;
import com.flex.tender.payload.response.AuthenticationResponse;

@Mapper(componentModel = "spring", uses = AuthorityMapper.class)
public interface AuthenticationDetailsMapper {
    
    PrincipalDetails toPrincipal(User principal);

    @Mapping(target = "userId", source = "id")
    AuthenticationResponse toResponse(PrincipalDetails authenticatedPrincipal);

}