package pl.com.tenderflex.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferStatusRepository;
import pl.com.tenderflex.dao.mapper.OfferStatusMapper;
import pl.com.tenderflex.model.OfferStatus;

@Repository
@RequiredArgsConstructor
public class OfferStatusRepositoryImpl implements OfferStatusRepository {

    public static final String GET_OFFER_STATUS_BY_TENDER_AND_BIDDER = "SELECT os.id, os.bidder, os.contractor, os.tender "
            + "FROM offer_statuses os "
            + "LEFT JOIN offers ofs ON ofs.status_id = os.id "
            + "WHERE tender_id = ? AND bidder_id = ?"; 
    
    private final JdbcTemplate jdbcTemplate;
    private final OfferStatusMapper offerStatusMapper;
 
    @Override
    public OfferStatus getByTenderAndBidder(Integer tenderId, Integer bidderId) {
        return jdbcTemplate.queryForObject(GET_OFFER_STATUS_BY_TENDER_AND_BIDDER, offerStatusMapper, tenderId, bidderId);
    }
}