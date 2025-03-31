package pl.com.tenderflex.controller;

import static pl.com.tenderflex.security.SecurityRoles.*;
import static org.springframework.http.MediaType.*;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.exception.FileNotExistsException;
import pl.com.tenderflex.payload.response.FileMetadataResponse;
import pl.com.tenderflex.service.FileStorageService;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    public static final String URI_FILES = "";
    public static final String URI_FILES_KEY = "/{key}";
    
    private final FileStorageService fileStorageService;

    @Secured({ CONTRACTOR, BIDDER })
    @PostMapping(value = URI_FILES, consumes = MULTIPART_FORM_DATA_VALUE)
    public FileMetadataResponse uploadFile(@RequestParam MultipartFile file)
            throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new FileNotExistsException("File is not exists");
        }
        return fileStorageService.upload(file);
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(value = URI_FILES_KEY, produces = APPLICATION_PDF_VALUE)
    public Resource findByKey(@PathVariable("key") String key) throws IOException {
        return fileStorageService.findByKey(key);
    }
    
}