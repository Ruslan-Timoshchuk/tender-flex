package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dao.mapper.TenderMapper;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Repository
@RequiredArgsConstructor
public class TenderDao implements TenderRepository {

    public static final String ADD_NEW_TENDER_QUERY = "INSERT INTO "
            + "tenders(organization_id, contractor_id, cpv_code, tender_type, details, min_price, max_price, currency_id, publication_date, deadline, "
            + "deadline_for_signed_contract, status, contract_url, award_decision_url, reject_decision_url) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_TENDERS_BY_CONTRACTOR_QUERY = "SELECT * FROM tenders "
            + "WHERE contractor_id = ? ORDER BY publication_date ASC LIMIT ? OFFSET ?";
    public static final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public static final String GET_TENDERS_BY_CONDITION_QUERY = "SELECT * FROM tenders ORDER BY publication_date ASC LIMIT ? OFFSET ?";
    public static final String COUNT_ALL_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public static final String GET_TENDER_BY_ID_QUERY = "SELECT * FROM tenders WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;
    private final TenderMapper tenderMapper;

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
            statement.setString(13, tender.getContractUrl());
            statement.setString(14, tender.getAwardDecisionUrl());
            statement.setString(15, tender.getRejectDecisionUrl());
            return statement;
        }, keyHolder);
        tender.setId(keyHolder.getKeyAs(Integer.class));
        return tender;
    }

    @Override
    public List<Tender> getByContractor(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_TENDERS_BY_CONTRACTOR_QUERY, tenderMapper, contractorId, amountTenders,
                amountTendersToSkip);
    }

    @Override
    public Integer countTendersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }

    @Override
    public List<Tender> getByCondition(Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_TENDERS_BY_CONDITION_QUERY, tenderMapper, amountTenders, amountTendersToSkip);
    }

    @Override
    public Integer countAllTenders() {
        return jdbcTemplate.queryForObject(COUNT_ALL_TENDERS_QUERY, Integer.class);
    }

    @Override
    public Tender getById(Integer tenderId) {
        return jdbcTemplate.queryForObject(GET_TENDER_BY_ID_QUERY, tenderMapper, tenderId);
    }
}