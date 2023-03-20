package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dao.mapper.TenderBidderMapperList;
import pl.com.tenderflex.dao.mapper.TenderContractorMapperList;
import pl.com.tenderflex.dao.mapper.TenderMapper;
import pl.com.tenderflex.dao.mapper.TotalMapper;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.Total;

@Repository
@RequiredArgsConstructor
public class TenderRepositoryImpl implements TenderRepository {

    public static final String ADD_NEW_TENDER_QUERY = "INSERT INTO "
            + "tenders(organization_id, contractor_id, cpv_code, tender_type, details, min_price, max_price, currency_id, publication_date, deadline, "
            + "deadline_for_signed_contract, status, contract_file_name, award_decision_file_name, reject_decision_file_name) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_TENDERS_BY_CONTRACTOR_QUERY = "SELECT ten.id, ten.contractor_id, ten.cpv_code, organization_name, ten.status, "
            + "ten.deadline, count(os.id) AS offers_total "
            + "FROM tenders ten "
            + "LEFT JOIN organizations org ON org.id = ten.organization_id "
            + "LEFT JOIN offers os ON os.tender_id = ten.id "
            + "WHERE ten.contractor_id = ? "
            + "GROUP BY ten.id, org.organization_name LIMIT ? OFFSET ?";
    public static final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public static final String GET_TENDERS_BY_CONDITION_QUERY = "SELECT ten.id, ten.contractor_id, ten.cpv_code, organization_name, ten.status, ten.deadline "
            + "FROM tenders ten LEFT JOIN organizations org ON org.id = ten.organization_id LIMIT ? OFFSET ?";
    public static final String COUNT_ALL_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public static final String GET_TENDER_BY_ID_QUERY = "SELECT ten.id, ten.contractor_id, ten.organization_id, org.organization_name, "
            + "org.national_registration_number, org.country_id, co.country_name, org.city, org.contact_person_id, cp.first_name, cp.last_name, "
            + "cp.phone, ten.cpv_code, ten.tender_type, ten.details, ten.min_price, ten.max_price, ten.currency_id, cur.currency_type, ten.publication_date, "
            + "ten.deadline, ten.deadline_for_signed_contract, ten.contract_file_name, ten.award_decision_file_name, ten.reject_decision_file_name "
            + "FROM tenders ten "
            + "LEFT JOIN organizations org ON org.id = ten.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN currencies cur ON cur.id = ten.currency_id "
            + "WHERE ten.id = ?";
    public static final String GET_TOTAL_BY_CONTRACTOR_QUERY = "SELECT COUNT(DISTINCT ten.id) AS tenders, COUNT(os.id) AS offers "
            + "FROM tenders ten LEFT JOIN offers os ON os.tender_id = ten.id WHERE contractor_id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final TenderContractorMapperList tenderContractorMapperList;
    private final TenderBidderMapperList tenderBidderMapperList;
    private final TenderMapper tenderMapper;
    private final TotalMapper totalMapper;
 
    @Override
    public Tender create(Tender tender, Integer contractorId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_QUERY, new String[] { "id" });
            statement.setInt(1, tender.getOrganization().getId());
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
    
    @Override
    public List<Tender> getByContractor(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_TENDERS_BY_CONTRACTOR_QUERY, tenderContractorMapperList, contractorId, amountTenders,
                amountTendersToSkip);
    }
    
    @Override
    public Integer countTendersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }
    
    @Override
    public List<Tender> getAll(Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_TENDERS_BY_CONDITION_QUERY, tenderBidderMapperList, amountTenders, amountTendersToSkip);
    }

    @Override
    public Integer countAllTenders() {
        return jdbcTemplate.queryForObject(COUNT_ALL_TENDERS_QUERY, Integer.class);
    }
    
    @Override
    public Tender getById(Integer tenderId) {
        return jdbcTemplate.queryForObject(GET_TENDER_BY_ID_QUERY, tenderMapper, tenderId);
    }
    
    @Override
    public Total getTotalTendersAndOffersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(GET_TOTAL_BY_CONTRACTOR_QUERY, totalMapper, contractorId);
    }
}