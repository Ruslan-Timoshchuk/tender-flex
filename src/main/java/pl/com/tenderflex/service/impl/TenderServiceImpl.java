package pl.com.tenderflex.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContactPersonRepository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.OfferStatusRepository;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.OfferStatus;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final ContactPersonRepository contactPersonRepository;
    private final OrganizationRepository organizationRepository;
    private final TenderRepository tenderRepository;
    private final OfferRepository offerRepository;
    private final OfferStatusRepository offerStatusRepository;

    @Override
    @Transactional
    public void createTender(TenderDetailsRequest tenderDetailsRequest, Integer contractorId) {
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        Organization organization = tender.getOrganization();
        ContactPerson contactPerson = contactPersonRepository.create(organization.getContactPerson());
        organization.setContactPerson(contactPerson);
        organization = organizationRepository.create(organization);
        tender.setOrganization(organization);
        tender.setUserId(contractorId);
        tenderRepository.create(tender, contractorId);
    }

    @Override
    public Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage,
            Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countTendersByContractor(contractorId);
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        List<ContractorTenderResponse> tenders = tenderRepository
                .getByContractor(contractorId, tendersPerPage, amountTendersToSkip).stream()
                .map(tenderMapper::tenderToContractorTenderResponse).toList();
        return new Page<>(currentPage, totalPages, tenders);
    }

    @Override
    public Page<BidderTenderResponse> getByBidder(Integer bidderId, Integer currentPage, Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countAllTenders();
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        List<BidderTenderResponse> tenders = new ArrayList<>();
        tenderRepository.getAll(tendersPerPage, amountTendersToSkip).forEach(tender -> {
            BidderTenderResponse tenderResponse = tenderMapper.tenderToBidderTenderResponse(tender);
            if (!offerRepository.isExistsOfferByTenderAndBidder(tender.getId(), bidderId)) {
                tenderResponse.setOfferStatus("Offer hasn't sent");
            } else {
                OfferStatus status = offerStatusRepository.getByTenderAndBidder(tender.getId(), bidderId);
                tenderResponse.setOfferStatus(status.getBidder());
                tenderResponse.setTenderStatus(status.getTender());
            }
            tenders.add(tenderResponse);
        });
        return new Page<>(currentPage, totalPages, tenders);
    }

    @Override
    public ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId) {
        return tenderMapper.tenderToContractorTenderDetailsResponse(tenderRepository.getById(tenderId));
    }

    @Override
    public BidderTenderDetailsResponse getTenderByOfferId(Integer offerId) {
        return tenderMapper.tenderToBidderTenderDetailsResponse(tenderRepository.getByOfferId(offerId));
    }

    @Override
    public BidderTenderDetailsResponse getByIdForBidder(Integer tenderId) {
        return tenderMapper.tenderToBidderTenderDetailsResponse(tenderRepository.getById(tenderId));
    }
}