package com.flex.tender.model;

import com.flex.tender.model.enumeration.EAuthority;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Authority {

    private final Integer id;
    private final EAuthority title;
    
}