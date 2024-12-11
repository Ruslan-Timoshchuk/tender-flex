package pl.com.tenderflex.companyprofile.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.companyprofile.model.CompanyProfile;

@Component
public class CompanyProfileMapper implements RowMapper<CompanyProfile> {

    @Override
    public CompanyProfile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCompanyProfile(resultSet);
    }

    public CompanyProfile mapCompanyProfile(ResultSet resultSet) {
        return null;
    }
    
}