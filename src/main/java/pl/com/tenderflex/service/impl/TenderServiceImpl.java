package pl.com.tenderflex.service.impl;

import static pl.com.tenderflex.model.ETenderStatus.*;
import static pl.com.tenderflex.model.EOfferStatus.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.security.impl.UserDetailsImpl;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {
    
    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public void createTender(TenderDetailsRequest tenderDetailsRequest, UserDetailsImpl contractor) {
        Tender tender = tenderMapper.tenderDetailsRequestToTender(tenderDetailsRequest);
        tender.setContractor(contractor);
        tender.setStatus(TENDER_IN_PROGRESS);
        tenderRepository.create(tender);
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
           offerRepository.getAllByBidder(bidderId).forEach(offer -> {
               if(!offer.getTender().equals(tender)) {      
                   tenderResponse.setOfferStatusBidder(OFFER_HAS_NOT_SENT.name());
               } else {
                   tenderResponse.setOfferStatusBidder(offer.getOfferStatusBidder().name());
                   tenderResponse.setOfferStatusContractor(offer.getOfferStatusContractor().name());
               }
           });      
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
        Tender tender = tenderRepository.getById(tenderId);
        BidderTenderDetailsResponse tenderDetails = tenderMapper.tenderToBidderTenderDetailsResponse(tender);
        offerRepository.getAllByBidder(bidderId).forEach(offer -> {
            if(!offer.getTender().equals(tender)) {
                tenderDetails.setOfferStatusBidder(OFFER_HAS_NOT_SENT.name());
            } else {
                tenderDetails.setOfferStatusBidder(offer.getOfferStatusBidder().name());
                tenderDetails.setTenderStatusContractor(tender.getStatus().name());
            }
        });
        return tenderDetails;
    }
}