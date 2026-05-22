package com.flex.tender.repository;

import com.flex.tender.model.FileMetadata;

public interface FileMetadataRepository {

    FileMetadata save(FileMetadata file);

    FileMetadata findById(Integer id);
    
}