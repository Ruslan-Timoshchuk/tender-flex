package pl.com.tenderflex.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.OfferStatusRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.OfferStatus;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String OFFER_HAS_NOT_SENT = "Offer has not sent";
    
    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final OfferRepository offerRepository;
    private final OfferStatusRepository offerStatusRepository;

    @Override
    @Transactional
    public void createTender(TenderDetailsRequest tenderDetailsRequest, Integer contractorId) {
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        tender.setContractorId(contractorId);
        tenderRepository.create(tender, contractorId);
    }

    @Override
    public Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage,
            Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countTendersByContractor(contractorId);
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        List<ContractorTenderResponse> tenders = tenderRepository
                .getByContractor(contractorId, tendersPerPage, amountTendersToSkip).stream()
                .map(tenderMapper::tenderToContractorTenderResponse).toList();
        return new Page<>(currentPage, totalPages, tenders);
    }

    @Override
    public Page<BidderTenderResponse> getByBidder(Integer bidderId, Integer currentPage, Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countAllTenders();
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        List<BidderTenderResponse> tenders = new ArrayList<>();
        tenderRepository.getAll(tendersPerPage, amountTendersToSkip).forEach(tender -> {
            BidderTenderResponse tenderResponse = tenderMapper.tenderToBidderTenderResponse(tender);
            if (!offerRepository.isExistsOfferByTenderAndBidder(tender.getId(), bidderId)) {
                tenderResponse.setOfferStatus(OFFER_HAS_NOT_SENT);
            } else {
                OfferStatus status = offerStatusRepository.getByTenderAndBidder(tender.getId(), bidderId);
                tenderResponse.setOfferStatus(status.getBidder());
                tenderResponse.setTenderStatus(status.getTender());
            }
            tenders.add(tenderResponse);
        });
        return new Page<>(currentPage, totalPages, tenders);
    }

    @Override
    public ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId) {
        return tenderMapper.tenderToContractorTenderDetailsResponse(tenderRepository.getById(tenderId));
    }

    @Override
    public BidderTenderDetailsResponse getTenderByOfferId(Integer offerId) {
        return tenderMapper.tenderToBidderTenderDetailsResponse(tenderRepository.getByOfferId(offerId));
    }

    @Override
    public BidderTenderDetailsResponse getByIdForBidder(Integer tenderId, Integer bidderId) {
        BidderTenderDetailsResponse tender = tenderMapper.tenderToBidderTenderDetailsResponse(tenderRepository.getById(tenderId));
        if (!offerRepository.isExistsOfferByTenderAndBidder(tenderId, bidderId)) {
            tender.setOfferStatus(OFFER_HAS_NOT_SENT);
        } else {
            OfferStatus status = offerStatusRepository.getByTenderAndBidder(tenderId, bidderId);
            tender.setOfferStatus(status.getBidder());
            tender.setTenderStatus(status.getTender());
        }
        return tender;
    }
}