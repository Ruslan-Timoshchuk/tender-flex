package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CurrencyMixins {

    public final String CURRENCY_QUERY_COLUMNS = """
            currency.id AS currency_id, 
            currency.code, 
            currency.symbol,
            """;
    public final String CURRENCY_JOIN_OFFERS = """
            currencies currency ON 
            currency.id = offer.currency_id""";
    public static final String FIND_ALL_QUERY = "SELECT id AS currency_id, code, symbol FROM currencies";
    public static final String FIND_BY_ID_QUERY = "SELECT id AS currency_id, code, symbol FROM currencies WHERE id = ?";

}