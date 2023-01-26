package pl.com.tenderflex.dao;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Repository
public class TenderDao {

    public static final String ADD_NEW_TENDER_QUERY = "INSERT INTO "
            + "tenders(organization_id, contractor_id, cpv_code, tender_type, details, min_price, max_price, currency, publication, deadline, "
            + "deadlineForSignedContract, contractFileName, awardDecisionFileName, rejectDecisionFileName) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;

    public TenderDao(JdbcTemplate jdbcTemplate, OrganizationDao organizationDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizationDao = organizationDao;
    }

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
            statement.setString(8, String.valueOf(tender.getCurrency()));
            statement.setObject(9, tender.getPublication());
            statement.setObject(10, tender.getDeadline());
            statement.setObject(11, tender.getDeadlineForSignedContract());
            statement.setString(12, tender.getContractFileName());
            statement.setString(13, tender.getAwardDecisionFileName());
            statement.setString(14, tender.getRejectDecisionFileName());
            return statement;
        }, keyHolder);
        tender.setId(keyHolder.getKeyAs(Integer.class));
        return tender;
    }
}