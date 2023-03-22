package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Component
@RequiredArgsConstructor
public class TenderBidderMapperList implements RowMapper<Tender> {
   
    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tender
                .builder()
                .id(resultSet.getInt("id"))
                .contractorId(resultSet.getInt("contractor_id"))
                .cpvCode(resultSet.getString("cpv_code"))
                .organization(Organization
                        .builder()
                        .name(resultSet.getString("organization_name"))
                        .build())
                .status(resultSet.getString("status"))
                .deadline(resultSet.getObject("deadline", LocalDate.class))
                .offerStatus(resultSet.getString("bidder_status"))
                .build();
    }
}