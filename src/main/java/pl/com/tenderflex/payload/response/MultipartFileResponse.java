package pl.com.tenderflex.payload.response;

import java.net.URL;
import lombok.Data;

@Data
public class MultipartFileResponse {

    private final URL fileUrl;
    
}