package com.flex.tender.repository;

import com.flex.tender.model.FileMetadata;

public interface FileRepository {

    FileMetadata save(FileMetadata file);
    
}