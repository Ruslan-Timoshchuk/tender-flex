package com.flex.tender.payload.mapper;

import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderResponse;

@Mapper(componentModel = "spring", uses = { CpvMapper.class, CompanyProfileMapper.class, TenderStatusLabelMapper.class,
        OfferStatusLabelMapper.class })
public interface TenderMapper {

    @Mapping(target = "publicationDate", source = "publication", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "yyyy-MM-dd")
    Tender toEntity(TenderRequest tenderRequest);

    TenderResponse toResponse(Tender tender);

    @Mapping(target = "tenderStatusLabel", source = "tenderStatus", qualifiedByName = "viewLabel")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "dd/MM/yyyy")
    ContractorTenderSummaryResponse toContractorTenderSummary(Integer id, Cpv cpv, String contractorName,
            ETenderStatus tenderStatus, LocalDate offerSubmissionDeadline, Integer offersAmount);

    @Mapping(target = "tenderStatusLabel", source = "tenderStatus", qualifiedByName = "viewLabel")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offerStatus", qualifiedByName = "bidderLabel")
    BidderTenderSummaryResponse toBidderTenderSummary(Integer id, Cpv cpv, String contractorName,
            ETenderStatus tenderStatus, LocalDate offerSubmissionDeadline, EOfferStatus offerStatus);

}