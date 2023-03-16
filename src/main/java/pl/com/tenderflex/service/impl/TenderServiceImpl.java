package pl.com.tenderflex.service.impl;

import org.springframework.beans.factory.annotation.Value;
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
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "IN PROGRESS";

    @Value("${tenders.per.page}")
    private Integer tendersPerPage;
    private final TenderMapper tenderMapper;
    private final ContactPersonRepository contactPersonRepository;
    private final OrganizationRepository organizationRepository;
    private final TenderRepository tenderRepository;

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
    public Integer getTendersAmountByContractor(Integer contractorId) {
        return tenderRepository.countTendersByContractor(contractorId);
    }

    @Override
    public Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage) {
        Integer amountTenders = currentPage * tendersPerPage;
        Integer amountTendersToSkip = (currentPage - 1) * 5;
        Integer allTendersAmount = tenderRepository.countTendersByContractor(contractorId);
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
            return new Page<>(currentPage, totalPages,
                    tenderRepository.getByContractor(contractorId, amountTenders, amountTendersToSkip).stream()
                            .map(tenderMapper::tenderToContractorTenderResponse).toList());
    }

    @Override
    public Page<BidderTenderResponse> getByCondition(Integer currentPage) {
        Integer amountTenders = currentPage * tendersPerPage;
        Integer amountTendersToSkip = (currentPage - 1) * 5;
        Integer allTendersAmount = tenderRepository.countAllTenders();
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages, tenderRepository.getAll(amountTenders, amountTendersToSkip)
                .stream().map(tenderMapper::tenderToBidderTenderResponse).toList());
    }

    @Override
    public ContractorTenderDetailsResponse getById(Integer tenderId) {
        return tenderMapper.tenderToContractorTenderDetailsResponse(tenderRepository.getById(tenderId));
    }
}