package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class TenderInListResponse<T> {

    private Integer tenderId;
    private String cpvCode;
    private String cpvDescription;
    private String officialName;
    private String tenderStatus;
    private String deadline;
    private T offValue;

}