package pl.com.tenderflex.service.impl;

import static java.util.UUID.*;
import static com.amazonaws.HttpMethod.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.FileRepository;
import pl.com.tenderflex.model.File;
import pl.com.tenderflex.payload.iresponse.response.FileMetadataResponse;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;
import pl.com.tenderflex.payload.mapstract.FileMapper;
import pl.com.tenderflex.service.FileStorageService;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    public static final String FILE_EXTENSION_SEPARATOR = ".";
    
    @Value("${bucket.name}")
    private String bucketName;
    @Value("${exp.time.presigned.url}")
    private Integer expTimePresignedUrl;
    private final AmazonS3 amazonS3Client;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public FileMetadataResponse upload(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.doesBucketExistV2(bucketName);
        String originalFilename = file.getOriginalFilename();
        StringBuilder fileKey = new StringBuilder();
        if (originalFilename != null && originalFilename.contains(FILE_EXTENSION_SEPARATOR)) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(FILE_EXTENSION_SEPARATOR));
            fileKey.append(randomUUID());
            fileKey.append(fileExtension);
        }
        amazonS3Client.putObject(bucketName, fileKey.toString(), file.getInputStream(), metadata);
        return fileMapper.fileToFileDetailsResponse(
                fileRepository.save(
                        File
                          .builder()
                          .name(originalFilename)
                          .contentType(file.getContentType())
                          .awsS3fileKey(fileKey.toString())
                          .build()));
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