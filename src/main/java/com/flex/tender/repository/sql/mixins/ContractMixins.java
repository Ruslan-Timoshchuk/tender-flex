package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractMixins {

    public final String CONTRACT_INSERT_COLUMNS = """
            award_decision_id, 
            contract_type_id, 
            min_price, 
            max_price, 
            currency_id, 
            file_metadata_id, 
            global_status, 
            signed_deadline
            """;
    public final String CONTRACT_INSERT_VALUE_PARAMETERS = """
            :awardDecisionId, 
            :contractTypeId, 
            :minPrice, 
            :maxPrice, 
            :currencyId, 
            :fileMetadataId, 
            :globalStatus, 
            :signedDeadline
            """;
    public final String CONTRACT_UPDATE_SET_CLAUSE = """
            contract_type_id = :contractTypeId, 
            min_price = :minPrice, 
            max_price = :maxPrice, 
            currency_id = :currencyId,
            file_id = :fileMetadataId, 
            global_status = :globalStatus, 
            signed_deadline = :signedDeadline, 
            signed_date = :signedDate
            """;
  
    public final String CONTRACT_QUERY_COLUMNS = """
            contract.id AS contract_id, 
            contract.min_price, 
            contract.max_price,
            contract.global_status, 
            contract.signed_deadline, 
            contract.signed_date
            """;
    
}