package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.CpvMixins.*;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Cpv;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.repository.extractor.OfferCpvExtractor;
import com.flex.tender.repository.extractor.TenderCpvExtractor;
import com.flex.tender.repository.mapper.CpvMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final CpvMapper cpvMapper;
    private final OfferCpvExtractor offerCpvExtractor;
    private final TenderCpvExtractor tenderCpvExtractor;

    @Override
    public List<Cpv> findAll() {
        return jdbc.query(FIND_ALL_QUERY, cpvMapper);
    }

    @Override
    public Map<Integer, Cpv> findByOfferIdIn(List<Integer> offerIds) {
        return jdbc.query(FIND_BY_OFFER_ID_IN_QUERY, Map.of("offerIds", offerIds), offerCpvExtractor);
    }
    
    @Override
    public Map<Integer, Cpv> findByTenderIdIn(List<Integer> tenderIds) {
        return jdbc.query(FIND_BY_TENDER_ID_IN_QUERY, Map.of("tenderIds", tenderIds), tenderCpvExtractor);
    }

    @Override
    public Cpv findById(Integer id) {
        return jdbc.queryForObject(FIND_BY_ID_QUERY, Map.of("id", id), cpvMapper);
    }

}