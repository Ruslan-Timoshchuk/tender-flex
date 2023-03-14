package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CurrencyRepository;
import pl.com.tenderflex.dao.impl.OrganizationDao;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TenderType;

@Component
@RequiredArgsConstructor
public class TenderMapper implements RowMapper<Tender> {

    private final OrganizationDao organizationDao;
    private final CurrencyRepository currencyRepository;

    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tender
                .builder()
                .id(resultSet.getInt("id"))
                .contractorId(resultSet.getInt("contractor_id"))
                .organization(organizationDao.getById(resultSet.getInt("organization_id")))
                .cpvCode(resultSet.getString("cpv_code"))
                .type(TenderType.valueOf(resultSet.getString("tender_type")))
                .details(resultSet.getString("details"))
                .minPrice(resultSet.getInt("min_price"))
                .maxPrice(resultSet.getInt("max_price"))
                .currency(currencyRepository.getById(resultSet.getInt("currency_id")))
                .publication(resultSet.getObject("publication_date", LocalDate.class))
                .deadline(resultSet.getObject("deadline", LocalDate.class))
                .deadlineForSignedContract(resultSet.getObject("deadline_for_signed_contract", LocalDate.class))
                .status(resultSet.getString("status"))
                .contractUrl(resultSet.getString("contract_url"))
                .awardDecisionUrl(resultSet.getString("award_decision_url"))
                .rejectDecisionUrl(resultSet.getString("reject_decision_url")).build();
    }
}