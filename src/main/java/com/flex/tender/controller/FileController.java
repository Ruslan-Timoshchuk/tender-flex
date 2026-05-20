package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.*;
import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.flex.tender.exception.FileNotExistsException;
import com.flex.tender.payload.response.FileMetadataResponse;
import com.flex.tender.service.FileStorageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.files.path}")
@RequiredArgsConstructor
public class FileController {

    public static final String URI_FILES_KEY = "/{key}";
    
    private final FileStorageService fileStorageService;

    @Secured({ CONTRACTOR, BIDDER })
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileMetadataResponse> uploadFile(@RequestParam MultipartFile file) throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new FileNotExistsException("File is not exists");
        }
        return ResponseEntity
                   .ok(fileStorageService.upload(file));
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(value = URI_FILES_KEY, produces = APPLICATION_PDF_VALUE)
    public Resource findByKey(@PathVariable("key") String key) throws IOException {
        return fileStorageService.findByKey(key);
    }
    
}