package pl.com.tenderflex.service;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.payload.response.FileMetadataResponse;

public interface FileStorageService {

    FileMetadataResponse upload(MultipartFile file) throws IOException;

    Resource findByKey(String key) throws IOException;

}