package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Repository
@RequiredArgsConstructor
public class TenderDao implements TenderRepository {

    public static final String ADD_NEW_TENDER_QUERY = "INSERT INTO "
            + "tenders(organization_id, contractor_id, cpv_code, tender_type, details, min_price, max_price, currency_id, publication_date, deadline, "
            + "deadline_for_signed_contract, status, contract_file_name, award_decision_file_name, reject_decision_file_name) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;

    @Override
    public Tender create(Tender tender, Integer contractorId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Organization organization = organizationDao.create(tender.getOrganization());
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_QUERY, new String[] { "id" });
            statement.setInt(1, organization.getId());
            statement.setInt(2, contractorId);
            statement.setString(3, tender.getCpvCode());
            statement.setString(4, String.valueOf(tender.getType()));
            statement.setString(5, tender.getDetails());
            statement.setLong(6, tender.getMinPrice());
            statement.setLong(7, tender.getMaxPrice());
            statement.setInt(8, tender.getCurrency().getId());
            statement.setObject(9, tender.getPublication());
            statement.setObject(10, tender.getDeadline());
            statement.setObject(11, tender.getDeadlineForSignedContract());
            statement.setString(12, tender.getStatus());
            statement.setString(13, tender.getContractFileName());
            statement.setString(14, tender.getAwardDecisionFileName());
            statement.setString(15, tender.getRejectDecisionFileName());
            return statement;
        }, keyHolder);
        tender.setId(keyHolder.getKeyAs(Integer.class));
        return tender;
    }
}