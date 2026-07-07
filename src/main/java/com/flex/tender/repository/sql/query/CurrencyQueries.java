package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CurrencyQueries {

    public static final String FIND_ALL_QUERY = "SELECT id AS currency_id, code, symbol FROM currencies";
    public static final String FIND_BY_ID_QUERY = "SELECT id AS currency_id, code, symbol FROM currencies WHERE id = ?";

}