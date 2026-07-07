package com.flex.tender.service.write;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.flex.tender.payload.response.FileMetadataResponse;

public interface FileStorageService {

    FileMetadataResponse upload(MultipartFile file) throws IOException;

}