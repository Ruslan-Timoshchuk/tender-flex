package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CurrencyMixins {

    public final String CURRENCY_QUERY_COLUMNS = """
            currency.id AS currency_id, 
            currency.code, 
            currency.symbol
            """;
    public final String CURRENCY_JOIN_OFFERS = """
            currencies currency ON 
            currency.id = offer.currency_id""";
    public final String CURRENCY_JOIN_CONTRACTS = """
            currencies currency ON 
            currency.id = contract.currency_id
            """;

}