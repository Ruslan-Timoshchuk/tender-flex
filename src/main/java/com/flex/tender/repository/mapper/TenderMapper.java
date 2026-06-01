package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.TenderColumns.*;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.Procedure;
import com.flex.tender.model.enumeration.ELanguage;
import com.flex.tender.model.enumeration.EProcedure;
import com.flex.tender.model.enumeration.ETenderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TenderMapper implements RowMapper<Tender> {
    
    private final CompanyProfileMapper companyProfileMapper;
    private final CpvMapper cpvMapper;
  
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