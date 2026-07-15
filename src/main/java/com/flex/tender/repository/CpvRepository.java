package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.Cpv;

public interface CpvRepository {

    List<Cpv> findAll();

    Cpv findById(Integer id);

}