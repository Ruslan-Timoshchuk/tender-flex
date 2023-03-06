package pl.com.tenderflex.controller;

import static org.springframework.http.HttpStatus.OK;
import java.io.IOException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.exception.FileNotExistsException;
import pl.com.tenderflex.payload.response.MultipartFileResponse;
import pl.com.tenderflex.service.FileStorageService;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    @ResponseStatus(OK)
    public MultipartFileResponse uploadDocument(@AuthenticationPrincipal(expression = "id") Integer userId,
            @RequestParam MultipartFile document) throws IOException {
        if (document.getOriginalFilename() == null) {
            throw new FileNotExistsException("File is not exists");
        }
        return fileStorageService.upload(document, userId);
    }
}