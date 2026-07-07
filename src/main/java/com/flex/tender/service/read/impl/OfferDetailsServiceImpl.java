package com.flex.tender.service.read.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Offer;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.mapper.OfferMapper;
import com.flex.tender.payload.response.BidderOfferDetailsResponse;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.service.read.OfferDetailsService;
import com.flex.tender.service.read.TenderDetailsBatchService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferDetailsServiceImpl implements OfferDetailsService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final TenderDetailsBatchService tenderDetailsBatchService;
    
    @Override
    public BidderOfferDetailsResponse findBidderOfferDetailsById(Integer id) { 
        return offerMapper.toBidderDetails(offerRepository.findById(id));
    }
    
    @Override
    public ContractorOfferDetailsResponse findContractorOfferDetailsById(Integer id) {
        return offerMapper.toContractorDetails(offerRepository.findById(id));
    }

    @Override
    public Offer findById(Integer offerId) {
        return offerRepository.findById(offerId);
    }
    
    @Override
    public SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer page,
            Integer pageSize) {
        Integer offset = page * pageSize;
        Integer totalOffers = offerRepository.countByContractor(contractorId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        var offers = offerRepository.findByContractorWithPagination(contractorId, pageSize, offset);
        List<OfferSummaryResponse> offersPage = List.of();
        if(!offers.isEmpty()) {
            var offerIds = offers
                    .stream()
                    .map(Offer::getId)
                    .toList();
            var tenders = tenderDetailsBatchService.findByOfferIdIn(offerIds);
            offersPage = offers.stream()
                  .map(offer -> offerMapper.toContractorSummaryResponse(offer, tenders.get(offer.getId())))
                  .toList();
        }
        return new SummaryPage<>(page, totalPages, offersPage);
    }
    
    private Integer countTotalPages(Integer pageSize, Integer totalOffers) {
        return ((totalOffers + pageSize - 1) / pageSize);
    }
    
    @Override
    public SummaryPage<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer page,
            Integer pageSize) {
        Integer offset = page * pageSize;
        Integer totalOffers = offerRepository.countByBidder(bidderId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        var offers = offerRepository.findByBidderWithPagination(bidderId, pageSize, offset);
        List<OfferSummaryResponse> offersPage = List.of();
        if(!offers.isEmpty()) {
            var offerIds = offers
                    .stream()
                    .map(Offer::getId)
                    .toList();
            var tenders = tenderDetailsBatchService.findByOfferIdIn(offerIds);
            offersPage = offers.stream()
                  .map(offer -> offerMapper.toBidderSummaryResponse(offer, tenders.get(offer.getId())))
                  .toList();
        }
        return new SummaryPage<>(page, totalPages, offersPage);
    }

    @Override
    public SummaryPage<TenderOfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer requestedPage,
            Integer pageSize) {
        Integer offset = requestedPage * pageSize;
        Integer totalOffers = offerRepository.countAllByTender(tenderId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        return new SummaryPage<>(requestedPage, totalPages,
                offerRepository.findByTenderWithPagination(tenderId, pageSize, offset).stream()
                        .map(offerMapper::toTenderSummaryResponse).toList());
    }
    
    @Override
    public OfferCountResponse countByBidder(Integer bidderId) {
        return new OfferCountResponse(offerRepository.countByBidder(bidderId));
    }

    @Override
    public OfferCountResponse countByContractor(Integer contractorId) {
        return new OfferCountResponse(offerRepository.countByContractor(contractorId));
    }
    
}