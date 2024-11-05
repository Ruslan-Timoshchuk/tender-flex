package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.File;
import pl.com.tenderflex.payload.iresponse.response.FileMetadataResponse;

@Mapper(componentModel = "spring")
public interface FileMapper {
   
    FileMetadataResponse fileToFileDetailsResponse(File file);

}