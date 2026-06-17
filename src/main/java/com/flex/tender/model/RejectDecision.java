package com.flex.tender.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RejectDecision {

    private Integer id;
    private Tender tender;
    private FileMetadata fileMetadata;
    
}