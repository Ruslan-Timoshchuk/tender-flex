package pl.com.tenderflex.repository.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Procedure;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ELanguage;
import pl.com.tenderflex.model.enums.EProcedure;
import pl.com.tenderflex.model.enums.ETenderStatus;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TenderMapper implements RowMapper<Tender> {
    
    public static final String TENDER_ID = "id";
    public static final String TENDER_DESCRIPTION = "description";
    public static final String GLOBAL_STATUS = "global_status";
    public static final String PUBLICATION_DATE = "publication_date";
    public static final String OFFER_SUBMISSION_DEADLINE = "offer_submission_deadline";
    public static final String PROCEDURE_TYPE = "procedure_type";
    public static final String PROCEDURE_LANGUAGE = "language";

    private final CompanyProfileMapper companyProfileMapper;
    private final CpvMapper cpvMapper;
    private final ContractMapper contractMapper;
    private final AwardDecisionMapper awardMapper;
    private final RejectDecisionMapper rejectMapper;
  
    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapTender(resultSet);
    }
    
    public Tender mapTender(ResultSet resultSet) throws SQLException { 
        return Tender
                .builder()
                .id(resultSet.getInt(TENDER_ID))
                .companyProfile(companyProfileMapper.mapCompanyProfile(resultSet))
                .procedure(mapProcedure(resultSet))
                .cpv(cpvMapper.mapCpv(resultSet))
                .description(resultSet.getString(TENDER_DESCRIPTION))
                .globalStatus(ETenderStatus.valueOf(resultSet.getString(GLOBAL_STATUS)))
                .contract(contractMapper.mapContract(resultSet))
                .awardDecision(awardMapper.mapAward(resultSet))
                .rejectDecision(rejectMapper.mapReject(resultSet))
                .publicationDate(resultSet.getObject(PUBLICATION_DATE, LocalDate.class))
                .offerSubmissionDeadline(resultSet.getObject(OFFER_SUBMISSION_DEADLINE, LocalDate.class))
                .build();
    }
    
    private Procedure mapProcedure(ResultSet resultSet) throws SQLException {
        return Procedure
                 .builder()
                 .type(EProcedure.valueOf(resultSet.getString(PROCEDURE_TYPE)))
                 .language(ELanguage.valueOf(resultSet.getString(PROCEDURE_LANGUAGE)))
                 .build();
    }
  
}