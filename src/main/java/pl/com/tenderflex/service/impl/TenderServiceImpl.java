package pl.com.tenderflex.service.impl;

import static java.util.Arrays.asList;
import java.io.IOException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.service.FileStorageService;
import pl.com.tenderflex.service.TenderService;

@Service
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "Tender in progress";

    private final TenderRepository tenderRepository;
    private final FileStorageService storageSevice;

    public TenderServiceImpl(TenderRepository tenderRepository, FileStorageService storageSevice) {
        this.tenderRepository = tenderRepository;
        this.storageSevice = storageSevice;
    }

    @Override
    @Transactional
    public void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId) {
        MultipartFile contract = attachment.getContract();
        MultipartFile awardDecisionDocument = attachment.getAwardDecision();
        MultipartFile rejectDecisionDocument = attachment.getRejectDecision();
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setFirstName(tenderDetails.getFirstName());
        contactPerson.setLastName(tenderDetails.getLastName());
        contactPerson.setPhone(tenderDetails.getPhone());
        Organization organization = new Organization();
        organization.setContactPerson(contactPerson);
        organization.setName(tenderDetails.getOrganizationName());
        organization.setNationalRegistrationNumber(tenderDetails.getNationalRegistrationNumber());
        organization.setCountry(tenderDetails.getCountry());
        organization.setCity(tenderDetails.getCity());
        Tender tender = new Tender(organization, tenderDetails.getPublication());
        tender.setContractorId(contractorId);
        tender.setCpvCode(tenderDetails.getCpvCode());
        tender.setType(tenderDetails.getType());
        tender.setDetails(tenderDetails.getDetails());
        tender.setMinPrice(tenderDetails.getMinPrice());
        tender.setMaxPrice(tenderDetails.getMaxPrice());
        tender.setCurrency(tenderDetails.getCurrency());
        tender.setDeadline(tenderDetails.getDeadline());
        tender.setDeadlineForSignedContract(tenderDetails.getDeadlineForSignedContract());
        tender.setStatus(TENDER_IN_PROGRESS);
        tender.setContractFileName(contract.getOriginalFilename());
        tender.setAwardDecisionFileName(awardDecisionDocument.getOriginalFilename());
        tender.setRejectDecisionFileName(rejectDecisionDocument.getOriginalFilename());
        try {
            Integer tenderId = tenderRepository.create(tender, contractorId).getId();
            storageSevice.upload(asList(contract, awardDecisionDocument, rejectDecisionDocument), contractorId,
                    tenderId);
        } catch (DataAccessException | IOException e) {
            throw new ServiceException("Error occurred when saving the tender", e);
        }
    }
}