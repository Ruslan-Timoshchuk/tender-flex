package com.flex.tender.payload.response;

import com.flex.tender.model.embedded.PrincipalAuthority;

/**
 * @author Ruslan Timoshchuk
 */
public record AuthorityResponse(
        Integer id, 
        String name, 
        String label) {

    public AuthorityResponse(PrincipalAuthority principalAuthority) {
        this(principalAuthority.id(), 
             principalAuthority.authority().name(), 
             principalAuthority.authority().getLabel());
    }

}