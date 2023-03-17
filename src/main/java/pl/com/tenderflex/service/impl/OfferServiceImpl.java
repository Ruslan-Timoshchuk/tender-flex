package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContactPersonRepository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.service.OfferService;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    public static final String OFFER_RECEIVED_STATUS = "OFFER RECEIVED";
    public static final String OFFER_SENT_STATUS = "OFFER SENT";
    
    @Value("${offers.contractor.per.page}")
    private Integer offersContractorPerPage;
    @Value("${offers.bidder.per.page}")
    private Integer offersBidderPerPage;
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
        offer.setPublicationDate(now());
        offerRepository.create(offer, bidderId);
    }
        
    @Override
    public Page<OfferResponse> getOffersByBidder(Integer bidderId, Integer currentPage) {
        Integer amountOffers = currentPage * offersBidderPerPage;
        Integer amountOffersToSkip = (currentPage - 1) * 5;
        Integer allOffersAmount = offerRepository.countOffersByBidder(bidderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersContractorPerPage) {
            totalPages = allOffersAmount / offersContractorPerPage;
            if (allOffersAmount % offersContractorPerPage > 0) {
                totalPages++;
            }
        }
            return new Page<>(currentPage, totalPages,
                    offerRepository.getByBidder(bidderId, amountOffers, amountOffersToSkip).stream()
                            .map(offerMapper::offerToOfferResponse).toList());
    }

    @Override
    public OfferDetailsResponse getById(Integer offerId) {
            return offerMapper.offerToOfferDetailsResponse(offerRepository.getById(offerId));
    } 
}