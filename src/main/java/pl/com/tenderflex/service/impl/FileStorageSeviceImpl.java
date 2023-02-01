package pl.com.tenderflex.service.impl;

import static org.apache.commons.io.FileUtils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.service.FileStorageService;

@Service
public class FileStorageSeviceImpl implements FileStorageService {

    public static final String SLASH_LINE = "/";

    @Value("${bucket.name}")
    private String bucketName;
    @Value("${upload.path}")
    private String uploadPath;
    private final AmazonS3 amazonS3Client;

    public FileStorageSeviceImpl(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public void upload(List<MultipartFile> files, Integer contractorId, Integer tenderId) throws IOException {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3Client).build();
        File uploadDir = new File(uploadPath);
        try {
            amazonS3Client.doesBucketExistV2(bucketName);
            StringBuilder directoryKeyPrefix = new StringBuilder();
            directoryKeyPrefix.append(contractorId);
            directoryKeyPrefix.append(SLASH_LINE);
            directoryKeyPrefix.append(tenderId);
            transferManager.uploadFileList(bucketName, directoryKeyPrefix.toString(), uploadDir,
                    files.stream().map(this::convertToFile).toList()).waitForCompletion();
        } catch (AmazonServiceException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServiceException("Error occurred when uploading to the bucket", e);
        } finally {
            transferManager.shutdownNow(false);
            deleteDirectory(uploadDir);
        }
    }

    @Override
    public void upload(MultipartFile document, Integer bidderId, Integer offerId) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(document.getContentType());
        metadata.setContentLength(document.getSize());
        amazonS3Client.doesBucketExistV2(bucketName);
        StringBuilder directoryKeyPrefix = new StringBuilder();
        directoryKeyPrefix.append(bidderId);
        directoryKeyPrefix.append(SLASH_LINE);
        directoryKeyPrefix.append(offerId);
        directoryKeyPrefix.append(SLASH_LINE);
        directoryKeyPrefix.append(document.getOriginalFilename());
        try {
            amazonS3Client.putObject(bucketName, directoryKeyPrefix.toString(), document.getInputStream(), metadata);
        } catch (IOException | AmazonServiceException e) {
            throw new ServiceException("Error occurred when uploading the document", e);
        }
    }

    private File convertToFile(MultipartFile multipartFile) {
        StringBuilder result = new StringBuilder();
        result.append(uploadPath);
        result.append(SLASH_LINE);
        result.append(multipartFile.getOriginalFilename());
        File file = new File(result.toString());
        try {
            touch(file);
            writeByteArrayToFile(file, multipartFile.getBytes());
        } catch (IOException e) {
            throw new ServiceException("Error occurred when converting from multipartFile to file", e);
        }
        return file;
    }
}