package com.flex.tender.repository.sql.column;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractColumns {

    public final String CONTRACT_ID = "contract_id";
    public final String MIN_PRICE = "min_price";
    public final String MAX_PRICE = "max_price";  
    public final String CONTRACT_FILE_ID = "contract_file_id";
    public final String CONTRACT_FILE_NAME = "contract_file_name";
    public final String CONTRACT_FILE_CONTENT_TYPE = "contract_file_content_type";
    public final String CONTRACT_FILE_AWS3_KEY = "contract_aws_s3_file_key"; 
    public final String GLOBAL_STATUS = "global_status";
    public final String SIGNED_DEADLINE = "signed_deadline";
    public final String SIGNED_DATE = "signed_date";
    
}