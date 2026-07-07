package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record FileMetadataResponse(
        Integer id, 
        String name, 
        String contentType, 
        String awsS3fileKey) {
}