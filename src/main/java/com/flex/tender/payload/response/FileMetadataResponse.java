package com.flex.tender.payload.response;

public record FileMetadataResponse(
        Integer id, 
        String name, 
        String contentType, 
        String awsS3fileKey) {
}