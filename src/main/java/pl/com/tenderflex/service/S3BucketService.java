package pl.com.tenderflex.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;

public interface S3BucketService {

    String upload(MultipartFile file) throws IOException;
    
    MultipartFileResponse getPresignedUrl(String fileName);

}