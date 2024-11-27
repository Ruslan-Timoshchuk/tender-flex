package pl.com.tenderflex.cpv.repository;

import java.util.List;
import java.util.Optional;
import pl.com.tenderflex.model.Cpv;

public interface CpvRepository {

    List<Cpv> findAll();

    Optional<Cpv> findById(Integer id);
    
}