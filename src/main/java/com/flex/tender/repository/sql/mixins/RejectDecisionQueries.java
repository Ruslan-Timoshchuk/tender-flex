package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RejectDecisionQueries {

    public final String ADD_NEW_REJECT_QUERY = "INSERT INTO rejects(tender_id, reject_file_id) VALUES (?, ?)";
    public final String FIND_BY_ID_QUERY = """
            SELECT reject.id AS reject_id, reject_file.id AS reject_file_id, reject_file.name AS reject_file_name,
            reject_file.content_type AS reject_file_content_type, reject_file.aws_s3_file_key AS reject_aws_s3_file_key
            FROM rejects reject
            LEFT JOIN files reject_file ON reject_file.id = reject.reject_file_id
            WHERE reject.id = ?
            """;
    public final String FIND_BY_TENDER_ID_QUERY = """
            SELECT rt.id AS reject_id, fe.id AS reject_file_id, fe.name AS reject_file_name,
            fe.content_type AS reject_file_content_type, fe.aws_s3_file_key AS reject_aws_s3_file_key
            FROM rejects rt
            LEFT JOIN files fe ON fe.id = rt.reject_file_id
            WHERE rt.tender_id = ?
            """;
    
}