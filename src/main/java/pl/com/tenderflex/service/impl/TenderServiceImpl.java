package pl.com.tenderflex.service.impl;

import static java.util.Arrays.asList;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.dto.TenderDetailsResponse;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.service.FileStorageService;
import pl.com.tenderflex.service.TenderService;

@Service
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "Tender in progress";

    @Value("${tenders.per.page}")
    private Integer tendersPerPage;
    private final TenderRepository tenderRepository;
    private final FileStorageService storageSevice;
    private final MapStructMapper tenderMapper;

    public TenderServiceImpl(TenderRepository tenderRepository, FileStorageService storageSevice,
            MapStructMapper tenderMapper) {
        this.tenderRepository = tenderRepository;
        this.storageSevice = storageSevice;
        this.tenderMapper = tenderMapper;
    }

    @Override
    @Transactional
    public void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId) {
        MultipartFile contract = attachment.getContract();
        MultipartFile awardDecisionDocument = attachment.getAwardDecisionDocument();
        MultipartFile rejectDecisionDocument = attachment.getRejectDecisionDocument();
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetails);
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

    @Override
    public Page<TenderDetailsResponse> getByContractor(Integer contractorId, Integer currentPage) {
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
        try {
            return new Page<>(currentPage, totalPages,
                    tenderRepository.getByContractor(contractorId, amountTenders, amountTendersToSkip).stream()
                            .map(tenderMapper::tenderToTenderDetailsResponse).toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when searching tenders by contractor", e);
        }
    }
}