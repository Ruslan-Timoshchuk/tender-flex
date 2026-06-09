package com.flex.tender.repository.sql.column;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OfferColumns {

    public final String OFFER_ID = "offer_id";
    public final String GLOBAL_STATUS = "global_status"; 
    public final String BID_PRICE = "bid_price";  
    public final String PUBLICATION_DATE = "publication_date";             
    public final String PROPOSITION_FILE_ID = "proposition_file_id";
    public final String PROPOSITION_FILE_NAME = "proposition_file_name";
    public final String PROPOSITION_FILE_CONTENT_TYPE = "proposition_file_content_type";
    public final String PROPOSITION_FILE_AWS3_KEY = "proposition_file_aws_s3_file_key"; 
    
}