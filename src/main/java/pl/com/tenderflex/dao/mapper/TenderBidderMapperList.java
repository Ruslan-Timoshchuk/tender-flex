package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CPV;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TenderStatus;

@Component
@RequiredArgsConstructor
public class TenderBidderMapperList implements RowMapper<Tender> {
   
    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tender
                .builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("contractor_id"))
                .organization(Organization
                        .builder()
                        .name(resultSet.getString("organization_name"))
                        .build())
                .cpv(CPV.builder()
                        .id(resultSet.getInt("cpv_id"))
                        .code(resultSet.getString("code"))
                        .description(resultSet.getString("description"))
                        .build())
                .status(TenderStatus
                        .builder()
                        .id(resultSet.getInt("status_id"))
                        .status(resultSet.getString("status"))
                        .build())
                .deadline(resultSet.getObject("deadline", LocalDate.class))
                .build();
    }
}