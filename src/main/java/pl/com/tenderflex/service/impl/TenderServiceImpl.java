package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContactPersonRepository;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.mapstract.TotalMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.payload.response.TotalResponse;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "IN PROGRESS";

    private final TenderMapper tenderMapper;
    private final ContactPersonRepository contactPersonRepository;
    private final OrganizationRepository organizationRepository;
    private final TenderRepository tenderRepository;
    private final TotalMapper totalMapper;

    @Override
    @Transactional
    public void createTender(TenderDetailsRequest tenderDetailsRequest, Integer contractorId) {
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        Organization organization = tender.getOrganization();
        ContactPerson contactPerson = contactPersonRepository.create(organization.getContactPerson());
        organization.setContactPerson(contactPerson);
        organization = organizationRepository.create(organization);
        tender.setOrganization(organization);
        tender.setContractorId(contractorId);
        tender.setStatus(TENDER_IN_PROGRESS);
        tenderRepository.create(tender, contractorId);
    }

    @Override
    public TotalResponse getTotalTendersAndOffersByContractor(Integer contractorId) {
        return totalMapper.totalToTotalResponse(tenderRepository.getTotalTendersAndOffersByContractor(contractorId));
    }

    @Override
    public Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage, Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countTendersByContractor(contractorId);
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                tenderRepository.getByContractor(contractorId, tendersPerPage, amountTendersToSkip).stream()
                        .map(tenderMapper::tenderToContractorTenderResponse).toList());
    }

    @Override
    public Page<BidderTenderResponse> getByCondition(Integer currentPage, Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countAllTenders();
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages, tenderRepository.getAll(tendersPerPage, amountTendersToSkip).stream()
                .map(tenderMapper::tenderToBidderTenderResponse).toList());
    }

    @Override
    public ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId) {
        return tenderMapper.tenderToContractorTenderDetailsResponse(tenderRepository.getById(tenderId));
    }

    @Override
    public BidderTenderDetailsResponse getByIdForBidder(Integer tenderId) {
        return tenderMapper.tenderToBidderTenderDetailsResponse(tenderRepository.getById(tenderId));
    }
}