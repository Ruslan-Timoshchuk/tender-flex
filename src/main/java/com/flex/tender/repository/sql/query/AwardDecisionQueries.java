package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AwardDecisionQueries {

    public final String ADD_NEW_AWARD_DECISION_QUERY = "INSERT INTO awards(tender_id, award_file_id) VALUES (?, ?)";
    public final String FIND_BY_ID_QUERY = """
            SELECT award.id AS award_id, award_file.id AS award_file_id, award_file.name AS award_file_name,
            award_file.content_type AS award_file_content_type, award_file.aws_s3_file_key AS award_aws_s3_file_key
            FROM awards award
            LEFT JOIN files award_file ON award_file.id = award.award_file_id
            WHERE award.id = ?
            """;
    public final String FIND_BY_TENDER_ID_QUERY = """
            SELECT ad.id AS award_id, fe.id AS award_file_id, fe.name AS award_file_name,
            fe.content_type AS award_file_content_type, fe.aws_s3_file_key AS award_aws_s3_file_key
            FROM awards ad
            LEFT JOIN files fe ON fe.id = ad.award_file_id 
            WHERE ad.tender_id = 1
            """;
    
}