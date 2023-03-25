package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.OfferStatus;

@Component
public class OfferStatusMapper implements RowMapper<OfferStatus> {

            @Override
            public OfferStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return OfferStatus
                        .builder()
                        .id(resultSet.getInt("id"))
                        .contractor(resultSet.getString("contractor"))
                        .bidder(resultSet.getString("bidder"))
                        .tender(resultSet.getString("tender"))
                        .build();
            }
        }