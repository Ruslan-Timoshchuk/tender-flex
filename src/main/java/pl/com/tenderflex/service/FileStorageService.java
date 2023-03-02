package pl.com.tenderflex.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    URL upload(MultipartFile document, Integer userId) throws IOException;

}