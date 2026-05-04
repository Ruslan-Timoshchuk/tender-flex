package com.flex.tender.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "id", "code", "summary" })
public class Cpv {

    private Integer id;
    private String code;
    private String summary;

}