package pl.com.tenderflex.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.payload.iresponse.response.FileMetadataResponse;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;

public interface FileStorageService {

    FileMetadataResponse upload(MultipartFile file) throws IOException;
    
    MultipartFileResponse getPresignedUrl(String fileName);

}