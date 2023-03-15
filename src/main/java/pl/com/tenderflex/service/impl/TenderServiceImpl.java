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
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "IN PROGRESS";

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
}