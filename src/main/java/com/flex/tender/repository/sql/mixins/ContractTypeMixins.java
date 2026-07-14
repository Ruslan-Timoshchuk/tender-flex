package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractTypeMixins {

    public final String CONTRACT_TYPE_QUERY_COLUMNS = """
            contract_type.id AS contract_type_id,
            contract_type.title AS contract_type_name
            """;
    public final String CONTRACT_TYPE_JOIN_CONTRACTS = """
            contract_types contract_type ON 
            contract_type.id = contract.contract_type_id
            """;

}