package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.MapStructMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "Tender in progress";

    private final MapStructMapper tenderMapper;
    private final TenderRepository tenderRepository;
    
    @Override
    @Transactional
    public void createTender(TenderDetailsRequest tenderDetailsRequest, Integer contractorId) {
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        tender.setContractorId(contractorId);
        tender.setStatus(TENDER_IN_PROGRESS);
        tenderRepository.create(tender, contractorId);
    }
}