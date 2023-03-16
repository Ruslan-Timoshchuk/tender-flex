package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.model.Offer;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    public static final String ADD_NEW_OFFER_QURY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency_id, contractor_status, bidder_status, document_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;
  
    @Override
    public Offer create(Offer offer, Integer bidderId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QURY, new String[] { "id" });
            statement.setInt(1, bidderId);
            statement.setInt(2, offer.getTenderId());
            statement.setInt(3, offer.getOrganization().getId());
            statement.setLong(4, offer.getBidPrice());
            statement.setInt(5, offer.getCurrency().getId());
            statement.setString(6, offer.getContractorStatus());
            statement.setString(7, offer.getBidderStatus());
            statement.setString(8, offer.getDocumentUrl());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }
}