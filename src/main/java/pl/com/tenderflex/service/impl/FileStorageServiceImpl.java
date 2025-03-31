package pl.com.tenderflex.service.impl;

import static java.util.UUID.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.FileMetadata;
import pl.com.tenderflex.payload.mapstract.FileMetadataMapper;
import pl.com.tenderflex.payload.response.FileMetadataResponse;
import pl.com.tenderflex.repository.FileRepository;
import pl.com.tenderflex.service.FileStorageService;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    public static final String FILE_EXTENSION_SEPARATOR = ".";
    
    @Value("${bucket.name}")
    private String bucketName;
    private final AmazonS3 amazonS3Client;
    private final FileRepository fileRepository;
    private final FileMetadataMapper fileMapper;

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
        return fileMapper.toResponse(
                fileRepository.save(
                        FileMetadata
                          .builder()
                          .name(originalFilename)
                          .contentType(file.getContentType())
                          .awsS3fileKey(fileKey.toString())
                          .build()));
    }
     
    @Override
    public Resource findByKey(String key) throws IOException {
        return new ByteArrayResource(amazonS3Client.getObject(bucketName, key).getObjectContent().readAllBytes());
    }

}