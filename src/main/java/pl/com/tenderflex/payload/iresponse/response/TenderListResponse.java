package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class TenderListResponse {

    private Integer id;
    private String cpvCode;
    private String cpvDescription;
    private String officialName;
    private String tenderStatus;
    private String deadline;

}