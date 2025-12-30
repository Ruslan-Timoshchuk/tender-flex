package com.flex.tender.model;

import com.flex.tender.model.enumeration.ERole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Authority {

    private final Integer id;
    private final ERole role;

    public String getRole() {
        return this.role.name();
    }

}