package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class CpvResponse {

    private final Integer id;
    private final String code;
    private final String description;
    
}