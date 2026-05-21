package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractTypeQueries {

    public final String FIND_ALL_QUERY = "SELECT id AS contract_type_id, title AS contract_type_name FROM contract_types";
    public final String FIND_BY_ID_QUERY = "SELECT id AS contract_type_id, title AS contract_type_name FROM contract_types WHERE id = ?";

}