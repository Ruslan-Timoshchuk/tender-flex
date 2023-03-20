package pl.com.tenderflex.payload.response;

import java.net.URL;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultipartFileResponse {

    private URL fileUrl;
    private String fileName;
    
}