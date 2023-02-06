package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TenderType;

@Component
public class TenderMapper implements RowMapper<Tender> {

    private final OrganizationMapper organizationMapper;

    public TenderMapper(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tender tender = new Tender(organizationMapper.mapRow(resultSet, rowNum),
                resultSet.getObject("publication_date", LocalDate.class));
        tender.setId(resultSet.getInt("tender_id"));
        tender.setContractorId(resultSet.getInt("contractor_id"));
        tender.setCpvCode(resultSet.getString("cpv_code"));
        tender.setType(TenderType.valueOf(resultSet.getString("tender_type")));
        tender.setDetails(resultSet.getString("details"));
        tender.setMinPrice(resultSet.getInt("min_price"));
        tender.setMaxPrice(resultSet.getInt("max_price"));
        tender.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        tender.setDeadline(resultSet.getObject("deadline", LocalDate.class));
        tender.setDeadlineForSignedContract(resultSet.getObject("deadline_for_signed_contract", LocalDate.class));
        tender.setStatus(resultSet.getString("status"));
        tender.setContractFileName(resultSet.getString("contract_file_name"));
        tender.setAwardDecisionFileName(resultSet.getString("award_decision_file_name"));
        tender.setRejectDecisionFileName(resultSet.getString("reject_decision_file_name"));
        return tender;
    }
}