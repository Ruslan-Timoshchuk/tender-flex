package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;

import com.flex.tender.model.CompanyProfile;
import com.flex.tender.payload.request.CompanyProfileRequest;
import com.flex.tender.payload.response.CompanyProfileResponse;

@Mapper(componentModel = "spring", uses = { CountryMapper.class })
public interface CompanyProfileMapper {

    CompanyProfile toEntity(CompanyProfileRequest companyProfile);

    CompanyProfileResponse toResponse(CompanyProfile companyProfile);

}