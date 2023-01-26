package pl.com.tenderflex.dto;

import org.springframework.web.multipart.MultipartFile;

public class Attachment {

    private MultipartFile contract;
    private MultipartFile awardDecisionDocument;
    private MultipartFile rejectDecisionDocument;

    public MultipartFile getContract() {
        return contract;
    }

    public void setContract(MultipartFile contract) {
        this.contract = contract;
    }

    public MultipartFile getAwardDecisionDocument() {
        return awardDecisionDocument;
    }

    public void setAwardDecisionDocument(MultipartFile awardDecisionDocument) {
        this.awardDecisionDocument = awardDecisionDocument;
    }

    public MultipartFile getRejectDecisionDocument() {
        return rejectDecisionDocument;
    }

    public void setRejectDecisionDocument(MultipartFile rejectDecisionDocument) {
        this.rejectDecisionDocument = rejectDecisionDocument;
    }
}