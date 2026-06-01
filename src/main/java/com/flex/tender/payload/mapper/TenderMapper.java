package com.flex.tender.payload.mapper;

import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.BidderTenderDetailsResponse;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderDetailsResponse;

@Mapper(componentModel = "spring", uses = { CompanyProfileMapper.class, CpvMapper.class, ContractMapper.class,
        AwardDecisionMapper.class, RejectDecisionMapper.class, TenderStatusLabelMapper.class,
        OfferStatusLabelMapper.class })
public interface TenderMapper {

    @Mapping(target = "cpv", ignore = true)
    @Mapping(target = "companyProfile", ignore = true)
    @Mapping(target = "publicationDate", source = "publication", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "yyyy-MM-dd")
    Tender toEntity(TenderRequest tenderRequest);

    @Mapping(target = "id", source = "tender.id")
    @Mapping(target = "companyProfile", source = "tender.companyProfile")
    @Mapping(target = "procedure.type", source = "tender.procedure.type.label")
    @Mapping(target = "procedure.language", source = "tender.procedure.language.label")
    @Mapping(target = "cpv", source = "tender.cpv")
    @Mapping(target = "description", source = "tender.description")
    @Mapping(target = "publicationDate", source = "tender.publicationDate")
    @Mapping(target = "offerSubmissionDeadline", source = "tender.offerSubmissionDeadline")
    @Mapping(target = "contract", source = "contract")
    @Mapping(target = "awardDecision", source = "awardDecision")
    @Mapping(target = "rejectDecision", source = "rejectDecision")
    ContractorTenderDetailsResponse toContractorTenderDetailsResponse(Tender tender, Contract contract,
            AwardDecision awardDecision, RejectDecision rejectDecision);

    @Mapping(target = "id", source = "tender.id")
    @Mapping(target = "companyProfile", source = "tender.companyProfile")
    @Mapping(target = "procedure.type", source = "tender.procedure.type.label")
    @Mapping(target = "procedure.language", source = "tender.procedure.language.label")
    @Mapping(target = "cpv", source = "tender.cpv")
    @Mapping(target = "description", source = "tender.description")
    @Mapping(target = "publicationDate", source = "tender.publicationDate")
    @Mapping(target = "offerSubmissionDeadline", source = "tender.offerSubmissionDeadline")
    @Mapping(target = "contract", source = "contract")
    BidderTenderDetailsResponse toBidderTenderDetailsResponse(Tender tender, Contract contract);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "tenderStatusName", source = "tenderStatus")
    @Mapping(target = "tenderStatusLabel", source = "tenderStatus", qualifiedByName = "viewLabel")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "dd/MM/yyyy")
    ContractorTenderSummaryResponse toContractorTenderSummary(Integer id, Cpv cpv, String contractorName,
            ETenderStatus tenderStatus, LocalDate offerSubmissionDeadline, Integer offersAmount);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "tenderStatusName", source = "tenderStatus")
    @Mapping(target = "tenderStatusLabel", source = "tenderStatus", qualifiedByName = "viewLabel")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offerStatus", qualifiedByName = "bidderLabel")
    BidderTenderSummaryResponse toBidderTenderSummary(Integer id, Cpv cpv, String contractorName,
            ETenderStatus tenderStatus, LocalDate offerSubmissionDeadline, EOfferStatus offerStatus);

}