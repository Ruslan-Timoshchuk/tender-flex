package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.FileMetadata;

public interface FileRepository {

    FileMetadata save(FileMetadata file);
    
}