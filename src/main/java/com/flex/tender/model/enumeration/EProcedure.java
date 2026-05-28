package com.flex.tender.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProcedure {

    OPEN_PROCEDURE("Open Procedure");
    
    private final String label;
    
}