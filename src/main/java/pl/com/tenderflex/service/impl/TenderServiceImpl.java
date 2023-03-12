package pl.com.tenderflex.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.MapStructMapper;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String TENDER_IN_PROGRESS = "IN PROGRESS";

    @Value("${tenders.per.page}")
    private Integer tendersPerPage;
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

    @Override
    public Integer getTendersAmountByContractor(Integer contractorId) {
        return tenderRepository.countTendersByContractor(contractorId);
    }

    @Override
    public List<ContractorTenderResponse> getByContractor(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip) {       
        return tenderRepository.getByContractor(contractorId, amountTenders, amountTendersToSkip).stream()
                        .map(tenderMapper::tenderToContractorTenderResponse).toList();
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
        return new Page<>(currentPage, totalPages, tenderRepository.getByCondition(amountTenders, amountTendersToSkip)
                .stream().map(tenderMapper::tenderToBidderTenderResponse).toList());
    }
}