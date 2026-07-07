package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.payload.response.FileMetadataResponse;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {
    
    FileMetadataResponse toResponse(FileMetadata fileMetadata);

}