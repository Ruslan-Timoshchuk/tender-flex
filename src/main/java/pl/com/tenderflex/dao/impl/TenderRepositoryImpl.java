package pl.com.tenderflex.dao.impl;

import static java.util.stream.Collectors.toSet;
import java.sql.PreparedStatement;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.dao.mapper.TenderMapper;
import pl.com.tenderflex.dao.mapper.TotalMapper;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.Total;

@Repository
@RequiredArgsConstructor
public class TenderRepositoryImpl implements TenderRepository {

    public static final String ADD_NEW_TENDER_QUERY = "INSERT INTO "
            + "tenders(contractor_id, official_name, registration_number, country_id, city, first_name, last_name, phone_number, "
            + "cpv_id, type_of_tender_id, details, max_price, min_price, currency_id, publication_date, deadline, "
            + "signed_contract_deadline, status, contract_file_name, award_decision_file_name, reject_decision_file_name) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String BASE_CELECT_QUERY = "SELECT ten.id, ten.contractor_id, ten.official_name, ten.registration_number, "
            + "ten.country_id, co.country_name, ten.city, ten.first_name, ten.last_name, "
            + "ten.phone_number, ten.cpv_id, cs.code, cs.description, ten.type_of_tender_id, tot.title, ten.details, ten.max_price, ten.min_price, "
            + "ten.currency_id, cur.currency_type, ten.publication_date, ten.deadline, ten.signed_contract_deadline, ten.status, "
            + "ten.contract_file_name, ten.award_decision_file_name, ten.reject_decision_file_name "
            + "FROM tenders ten "
            + "LEFT JOIN countries co ON co.id = ten.country_id "
            + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "LEFT JOIN types_of_tender tot ON tot.id = ten.type_of_tender_id "
            + "LEFT JOIN currencies cur ON cur.id = ten.currency_id ";
    public static final String GET_TENDER_BY_ID_QUERY = BASE_CELECT_QUERY
            + "WHERE ten.id = ?";
    public static final String GET_TENDERS_BY_CONTRACTOR_QUERY = BASE_CELECT_QUERY 
            + "WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String GET_ALL_TENDERS = BASE_CELECT_QUERY 
            + "LIMIT ? OFFSET ?";
    public static final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public static final String COUNT_ALL_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public static final String GET_TENDER_BY_OFFER_QUERY = "SELECT ten.id, ten.contractor_id, ten.organization_id, org.organization_name, "
            + "org.national_registration_number, org.country_id, co.country_name, org.city, org.contact_person_id, cp.first_name, cp.last_name, "
            + "cp.phone, ten.cpv_id, cs.code, cs.description, ten.tender_type, ten.status_id, ts.status, ten.details, ten.min_price, ten.max_price, ten.currency_id, cur.currency_type, "
            + "ten.publication_date, ten.deadline, ten.deadline_for_signed_contract, ten.contract_file_name, ten.award_decision_file_name, ten.reject_decision_file_name "
            + "FROM tenders ten "
            + "LEFT JOIN organizations org ON org.id = ten.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "LEFT JOIN tender_statuses ts ON ts.id = ten.status_id "
            + "LEFT JOIN currencies cur ON cur.id = ten.currency_id "
            + "LEFT JOIN offers ofs ON ofs.tender_id = ten.id "
            + "WHERE ofs.id = ?";
    public static final String GET_TOTAL_BY_CONTRACTOR_QUERY = "SELECT COUNT(DISTINCT ten.id) AS tenders, COUNT(os.id) AS offers "
            + "FROM tenders ten LEFT JOIN offers os ON os.tender_id = ten.id WHERE contractor_id = ?";
    public static final String UPDATE_TENDER_STATUS_QUERY = "UPDATE tenders SET status_id = ? WHERE id = ?";
    public static final String GET_REJECT_FILE_NAME_BY_TENDER_QUERY = "SELECT reject_decision_file_name FROM tenders WHERE id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final TenderMapper tenderMapper;
    private final TotalMapper totalMapper;
    
    @Override
    public Tender create(Tender tender) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_QUERY, new String[] { "id" });
            statement.setInt(1, tender.getContractor().getId());
            statement.setString(2, tender.getContractorCompanyDetails().getOfficialName());
            statement.setString(3, tender.getContractorCompanyDetails().getRegistrationNumber());
            statement.setInt(4, tender.getContractorCompanyDetails().getCountry().getId());
            statement.setString(5, tender.getContractorCompanyDetails().getCity());
            statement.setString(6, tender.getContactPerson().getFirstName());
            statement.setString(7, tender.getContactPerson().getLastName());
            statement.setString(8, tender.getContactPerson().getPhoneNumber());
            statement.setInt(9, tender.getCpv().getId());
            statement.setInt(10, tender.getType().getId());
            statement.setString(11, tender.getDetails());
            statement.setLong(12, tender.getMaxPrice());
            statement.setLong(13, tender.getMinPrice());
            statement.setInt(14, tender.getCurrency().getId());
            statement.setObject(15, tender.getPublication());
            statement.setObject(16, tender.getDeadline());
            statement.setObject(17, tender.getSignedContractDeadline());
            statement.setString(18, tender.getStatus().name());
            statement.setString(19, tender.getContractFileName());
            statement.setString(20, tender.getAwardDecisionFileName());
            statement.setString(21, tender.getRejectDecisionFileName());
            return statement;
        }, keyHolder);
        tender.setId(keyHolder.getKeyAs(Integer.class));
        return tender;
    }
    
    @Override
    public Set<Tender> getByContractor(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_TENDERS_BY_CONTRACTOR_QUERY, tenderMapper, contractorId, amountTenders,
                amountTendersToSkip).stream().collect(toSet());
    }
    
    @Override
    public Integer countTendersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }
    
    @Override
    public Set<Tender> getTendersPage(Integer amountTenders, Integer amountTendersToSkip) {
        return jdbcTemplate.query(GET_ALL_TENDERS, tenderMapper, amountTenders, amountTendersToSkip).stream()
                .collect(toSet());
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
    public Tender getByOfferId(Integer offerId) {
        return jdbcTemplate.queryForObject(GET_TENDER_BY_OFFER_QUERY, tenderMapper, offerId);
    }
    
    @Override
    public Total getTotalTendersAndOffersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(GET_TOTAL_BY_CONTRACTOR_QUERY, totalMapper, contractorId);
    }
    
    @Override
    public void updateTenderStatus(Integer statusId, Integer tenderId) {
        jdbcTemplate.update(UPDATE_TENDER_STATUS_QUERY, statusId, tenderId);
    }

    @Override
    public String getRejectDecisionFileNameByTender(Integer tenderId) {
        return jdbcTemplate.queryForObject(GET_REJECT_FILE_NAME_BY_TENDER_QUERY, String.class, tenderId);
    }
}