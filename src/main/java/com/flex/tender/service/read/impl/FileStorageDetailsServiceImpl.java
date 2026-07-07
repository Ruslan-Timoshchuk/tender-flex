package com.flex.tender.service.read.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amazonaws.services.s3.AmazonS3;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.repository.FileMetadataRepository;
import com.flex.tender.service.read.FileStorageDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileStorageDetailsServiceImpl implements FileStorageDetailsService {

    @Value("${bucket.name}")
    private String bucketName;
    private final AmazonS3 amazonS3Client;
    private final FileMetadataRepository fileRepository;

    @Override
    public Resource findByKey(String key) throws IOException {
        return new ByteArrayResource(amazonS3Client.getObject(bucketName, key).getObjectContent().readAllBytes());
    }

    @Override
    public FileMetadata findById(Integer id) {
        return fileRepository.findById(id);
    }

}