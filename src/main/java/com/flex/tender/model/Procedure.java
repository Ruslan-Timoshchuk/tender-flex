package com.flex.tender.model;

import com.flex.tender.model.enumeration.ELanguage;
import com.flex.tender.model.enumeration.EProcedure;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Procedure {

    private EProcedure type;
    private ELanguage language;
          
} 