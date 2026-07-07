package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Authority;
import com.flex.tender.model.embedded.PrincipalAuthority;
import com.flex.tender.payload.response.AuthorityResponse;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    
    @Mapping(target = "authority", source = "title")
    PrincipalAuthority toPrincipal(Authority authority);
    
    @Mapping(target = "name", source = "authority")
    @Mapping(target = "label", source = "authority.label")
    AuthorityResponse toResponse(PrincipalAuthority principalAuthority);

}