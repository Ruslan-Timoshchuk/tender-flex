package pl.com.tenderflex.service;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dao.TenderDao;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Tender;

@Service
public class TenderService {

    @Value("${upload.path}")
    private String uploadPath;
    private final TenderDao tenderDao;

    public TenderService(TenderDao tenderDao) {
        this.tenderDao = tenderDao;
    }

    @Transactional
    public void createTender(Attachment attachment, Tender tender, Integer contractorId) {
        try {
            MultipartFile contract = attachment.getContract();
            StringBuilder contractFilename = new StringBuilder();
            contractFilename.append(contractorId);
            contractFilename.append(".");
            contractFilename.append(contract.getOriginalFilename());
            tender.setContractFileName(contractFilename.toString());

            MultipartFile awardDecisionDocument = attachment.getAwardDecisionDocument();
            StringBuilder awardDecisionFilename = new StringBuilder();
            awardDecisionFilename.append(contractorId);
            awardDecisionFilename.append(".");
            awardDecisionFilename.append(awardDecisionDocument.getOriginalFilename());
            tender.setAwardDecisionFileName(awardDecisionFilename.toString());

            MultipartFile rejectDecisionDocument = attachment.getRejectDecisionDocument();
            StringBuilder rejectDecisionFilename = new StringBuilder();
            rejectDecisionFilename.append(contractorId);
            rejectDecisionFilename.append(".");
            rejectDecisionFilename.append(rejectDecisionDocument.getOriginalFilename());
            tender.setRejectDecisionFileName(rejectDecisionFilename.toString());

            tenderDao.create(tender, contractorId);

            contract.transferTo(new File(uploadPath + contractFilename));
            awardDecisionDocument.transferTo(new File(uploadPath + awardDecisionFilename));
            rejectDecisionDocument.transferTo(new File(uploadPath + rejectDecisionFilename));
        } catch (DataAccessException | IllegalStateException | IOException e) {
            throw new ServiceException("Error occurred when saving the tender", e);
        }
    }
}