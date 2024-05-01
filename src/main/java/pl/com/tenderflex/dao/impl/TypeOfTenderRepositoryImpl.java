package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TypeOfTenderRepository;
import pl.com.tenderflex.dao.mapper.TypeOfTenderMapper;
import pl.com.tenderflex.model.TypeOfTender;

@Repository
@RequiredArgsConstructor
public class TypeOfTenderRepositoryImpl implements TypeOfTenderRepository {

    public static final String GET_ALL_TYPES_OF_TENDER_QUERY = "SELECT id, title FROM types_of_tender";
    
    private final JdbcTemplate jdbcTemplate;
    private final TypeOfTenderMapper typeOfTenderMapper;
    
    @Override
    public List<TypeOfTender> getAll() {
        return jdbcTemplate.query(GET_ALL_TYPES_OF_TENDER_QUERY, typeOfTenderMapper);
    }
}