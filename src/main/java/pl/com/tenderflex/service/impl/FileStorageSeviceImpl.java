package pl.com.tenderflex.service.impl;

import static java.util.UUID.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.MultipartFileResponse;
import pl.com.tenderflex.service.FileStorageService;

@Service
@RequiredArgsConstructor
public class FileStorageSeviceImpl implements FileStorageService {

    public static final String SLASH_LINE = "/";
    public static final String LOW_LINE = "_";

    @Value("${bucket.name}")
    private String bucketName;
    @Value("${upload.path}")
    private String uploadPath;
    private final AmazonS3 amazonS3Client;

    @Override
    public MultipartFileResponse upload(MultipartFile document, Integer userId) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(document.getContentType());
        metadata.setContentLength(document.getSize());
        amazonS3Client.doesBucketExistV2(bucketName);
        StringBuilder directoryKeyPrefix = new StringBuilder();
        directoryKeyPrefix.append(userId);
        directoryKeyPrefix.append(SLASH_LINE);
        directoryKeyPrefix.append(randomUUID());
        directoryKeyPrefix.append(LOW_LINE);
        directoryKeyPrefix.append(document.getOriginalFilename());
        amazonS3Client.putObject(bucketName, directoryKeyPrefix.toString(), document.getInputStream(), metadata);
        return new MultipartFileResponse(amazonS3Client.getUrl(bucketName, directoryKeyPrefix.toString()));
    }
}