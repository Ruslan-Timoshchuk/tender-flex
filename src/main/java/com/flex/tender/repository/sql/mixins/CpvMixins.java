package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpvMixins {

    public final String CPV_QUERY_COLUMNS = """
            cpv.id AS cpv_id, 
            cpv.code, 
            cpv.summary
            """;
    public final String CPV_JOIN_TENDERS = """
            cpvs cpv ON 
            cpv.id = tender.cpv_id""";
    
}