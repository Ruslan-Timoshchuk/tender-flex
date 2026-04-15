package com.flex.tender.model.embedded;

import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

/**
 * @author Ruslan Timoshchuk
 */
public record PrincipalAuthority(
        Integer id, 
        EAuthority authority) {

    public PrincipalAuthority(Authority authority) {
        this(authority.getId(), 
             authority.getTitle());
    }

}