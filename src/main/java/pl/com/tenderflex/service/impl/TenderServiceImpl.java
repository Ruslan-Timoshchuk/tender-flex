package pl.com.tenderflex.service.impl;

import static java.util.Arrays.asList;
import java.io.IOException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.service.FileStorageService;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "Tender in progress";

    private final MapStructMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final FileStorageService storageSevice;

    @Override
    @Transactional
    public void createTender(Attachment attachment, TenderDetailsRequest tenderDetailsRequest, Integer contractorId) {
        MultipartFile contract = attachment.getContract();
        MultipartFile awardDecisionDocument = attachment.getAwardDecision();
        MultipartFile rejectDecisionDocument = attachment.getRejectDecision();
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        tender.setContractorId(contractorId);
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