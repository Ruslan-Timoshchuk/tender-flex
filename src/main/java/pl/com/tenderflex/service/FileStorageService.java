package pl.com.tenderflex.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import pl.com.tenderflex.payload.response.MultipartFileResponse;

public interface FileStorageService {

    MultipartFileResponse upload(MultipartFile document, Integer userId) throws IOException;

}