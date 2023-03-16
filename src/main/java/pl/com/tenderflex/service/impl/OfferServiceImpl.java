package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContactPersonRepository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.service.OfferService;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    public static final String OFFER_RECEIVED_STATUS = "OFFER RECEIVED";
    public static final String OFFER_SENT_STATUS = "OFFER SENT";
    
    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final OrganizationRepository organizationRepository;
  
    @Override
    @Transactional
    public void createOffer(OfferDetailsRequest offerDetailsRequest, Integer bidderId) {
        Offer offer = offerMapper.offerDetailsRequestToOffer(offerDetailsRequest);
        Organization organization = offer.getOrganization();
        ContactPerson contactPerson = contactPersonRepository.create(organization.getContactPerson());  
        organization.setContactPerson(contactPerson);
        organization = organizationRepository.create(organization);
        offer.setOrganization(organization);
        offer.setBidderId(bidderId);
        offer.setContractorStatus(OFFER_RECEIVED_STATUS);
        offer.setBidderStatus(OFFER_SENT_STATUS);
        offerRepository.create(offer, bidderId);
    }
}