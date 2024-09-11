package pl.com.tenderflex.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderFileRepository;
import pl.com.tenderflex.exception.FileUploadException;
import pl.com.tenderflex.model.EFileType;
import pl.com.tenderflex.model.TenderFile;
import pl.com.tenderflex.service.S3BucketService;
import pl.com.tenderflex.service.TenderFileService;

@RequiredArgsConstructor
public class TenderFileServiceImpl implements TenderFileService {

    private final S3BucketService s3BucketService;
    private final TenderFileRepository tenderFileRepository;
    
    @Override
    public Set<TenderFile> saveFiles(Map<EFileType, MultipartFile> tenderFiles) {
        return tenderFiles.entrySet().stream()
                .map(entry -> {
                    try {
                        return tenderFileRepository
                        .create(TenderFile.builder()
                                 .name(entry.getValue().getOriginalFilename())
                                 .type(entry.getKey())
                                 .contentType(entry.getValue().getContentType())
                                 .awsS3fileKey(s3BucketService.upload(entry.getValue()))
                                 .build());
                    } catch (IOException e) {
                        throw new FileUploadException("Failed to upload file to S3", e);
                    }
                })
        .collect(Collectors.toSet());
    }   
}