package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class CPVresponse {

    private final Integer id;
    private final String code;
    private final String description;
    
}