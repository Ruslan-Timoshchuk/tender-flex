package pl.com.tenderflex.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.model.enums.EFileType;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;

public interface FileStorageService {

    Integer upload(MultipartFile file, EFileType type) throws IOException;
    
    MultipartFileResponse getPresignedUrl(String fileName);

}