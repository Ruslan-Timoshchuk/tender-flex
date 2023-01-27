package pl.com.tenderflex.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void upload(List<MultipartFile> files, Integer contractorId, Integer tenderId) throws IOException;

}