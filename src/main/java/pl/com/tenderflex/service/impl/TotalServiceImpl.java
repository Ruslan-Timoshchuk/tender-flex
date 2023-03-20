package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.payload.mapstract.TotalMapper;
import pl.com.tenderflex.payload.response.TotalResponse;
import pl.com.tenderflex.service.TotalService;

@Service
@RequiredArgsConstructor
public class TotalServiceImpl implements TotalService {

    private final TenderRepository tenderRepository;
    private final OfferRepository offerRepository;
    private final TotalMapper totalMapper;

    @Override
    public TotalResponse getTotalTendersAndOffersByContractor(Integer contractorId) {
        return totalMapper.totalToTotalResponse(tenderRepository.getTotalTendersAndOffersByContractor(contractorId));
    }

    @Override
    public TotalResponse getTotalTendersAndOffersByBidder(Integer bidderId) {
        return totalMapper.totalToTotalResponse(offerRepository.getTotalTendersAndOffersByBidder(bidderId));
    }
}