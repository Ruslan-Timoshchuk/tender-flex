package com.flex.tender.repository.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Cpv;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.repository.extractor.OfferCpvExtractor;
import com.flex.tender.repository.mapper.CpvMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpvRepositoryImpl.class);
    
    public static final String FIND_ALL_CPVS_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs";
    public static final String FIND_BY_OFFER_ID_IN_QUERY = """
            SELECT c.id AS cpv_id, code, summary, o.id AS offer_id 
            FROM offers o
            JOIN tenders t ON o.tender_id = t.id
            JOIN cpvs c ON t.cpv_id = c.id
            WHERE o.id IN (:offerIds)""";
      
    private final NamedParameterJdbcTemplate jdbc;
    private final CpvMapper cpvMapper;
    private final OfferCpvExtractor offerCpvExtractor;

    @Override
    public List<Cpv> findAll() {
        List<Cpv> cpvs = jdbc.query(FIND_ALL_CPVS_QUERY, cpvMapper);
        LOGGER.info("Successfully fetched {} cpvs", cpvs.size());
        return cpvs;
    }

    @Override
    public Map<Integer, Cpv> findByOfferIdIn(List<Integer> offerIds) {
        return jdbc.query(FIND_BY_OFFER_ID_IN_QUERY, Map.of("offerIds", offerIds), offerCpvExtractor);
    }

}