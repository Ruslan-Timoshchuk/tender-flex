package pl.com.tenderflex.service;

import java.util.Map;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.model.EFileType;
import pl.com.tenderflex.model.TenderFile;

public interface TenderFileService {
    
    Set<TenderFile> saveFiles(Map<EFileType, MultipartFile> tenderFiles);
    
}