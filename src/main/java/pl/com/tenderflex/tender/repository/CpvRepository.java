package pl.com.tenderflex.tender.repository;

import java.util.List;
import java.util.Optional;

import pl.com.tenderflex.tender.model.Cpv;

public interface CpvRepository {

    List<Cpv> findAll();

    Optional<Cpv> findById(Integer id);
    
}