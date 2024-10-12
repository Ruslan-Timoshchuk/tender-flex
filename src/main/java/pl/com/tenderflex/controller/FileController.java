package pl.com.tenderflex.controller;

import java.io.IOException;
import static org.springframework.http.MediaType.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.exception.FileNotExistsException;
import pl.com.tenderflex.model.enums.EFileType;
import pl.com.tenderflex.payload.iresponse.response.MultipartFileResponse;
import pl.com.tenderflex.service.FileStorageService;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @Secured({ "BIDDER", "CONTRACTOR" })
    @PostMapping(path = "/upload", consumes = { MULTIPART_FORM_DATA_VALUE })
    public Integer uploadFile(@RequestParam MultipartFile file, @RequestPart EFileType type)
            throws IOException {
        if (file.getOriginalFilename() == null) {
            throw new FileNotExistsException("File is not exists");
        }
        return fileStorageService.upload(file, type);
    }

    @Secured({ "BIDDER", "CONTRACTOR" })
    @GetMapping("/location/{document_name}")
    public MultipartFileResponse getPresignedUrl(@PathVariable("document_name") String documentName) {
        return fileStorageService.getPresignedUrl(documentName);
    }
}