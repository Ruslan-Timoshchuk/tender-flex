package com.flex.tender.service.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.service.facade.OfferManager;
import com.flex.tender.service.write.CompanyProfileService;
import com.flex.tender.service.write.OfferService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferManagerImpl implements OfferManager {

    private final OfferService offerService;
    private final CompanyProfileService companyProfileService;
    
    @Override
    public OfferSummaryResponse save(PrincipalSummary principalSummary, OfferRequest offerRequest) {
        Offer offer = offerService.buildEntity(principalSummary, offerRequest);
        CompanyProfile bidderCompanyProfile = companyProfileService.buildEntity(offerRequest.companyProfile());
        bidderCompanyProfile = companyProfileService.save(bidderCompanyProfile);
        offer.setCompanyProfile(bidderCompanyProfile);
        return offerService.save(offer);
    }
    
}