package pl.com.tenderflex.service.impl;

import static java.util.UUID.*;
import static com.amazonaws.HttpMethod.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;
import pl.com.tenderflex.service.S3BucketService;

@Service
@RequiredArgsConstructor
public class S3BucketServiceImpl implements S3BucketService {

    @Value("${bucket.name}")
    private String bucketName;
    @Value("${exp.time.presigned.url}")
    private Integer expTimePresignedUrl;
    private final AmazonS3 amazonS3Client;

    @Override
    public String upload(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.doesBucketExistV2(bucketName);
        String fileKey = randomUUID().toString();
        amazonS3Client.putObject(bucketName, fileKey, file.getInputStream(), metadata);
        return fileKey;
    }
    
    @Override
    public MultipartFileResponse getPresignedUrl(String fileKey) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += expTimePresignedUrl;
        expiration.setTime(expTimeMillis); 
        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileKey)
                .withMethod(GET)
                .withExpiration(expiration);
      return MultipartFileResponse.builder().fileUrl(amazonS3Client.generatePresignedUrl(presignedUrlRequest)).build();
    }
}