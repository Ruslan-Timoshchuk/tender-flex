package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.FileMetadata;
import pl.com.tenderflex.payload.request.FileMetadataRequest;
import pl.com.tenderflex.payload.response.FileMetadataResponse;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {
    
    FileMetadata toEntity(FileMetadataRequest fileMetadataRequest);
    
    FileMetadataResponse toResponse(FileMetadata fileMetadata);

}