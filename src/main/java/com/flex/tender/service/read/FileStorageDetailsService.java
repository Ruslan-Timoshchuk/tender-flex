package com.flex.tender.service.read;

import java.io.IOException;
import org.springframework.core.io.Resource;
import com.flex.tender.model.FileMetadata;

public interface FileStorageDetailsService {

    Resource findByKey(String key) throws IOException;

    FileMetadata findById(Integer id);

}
