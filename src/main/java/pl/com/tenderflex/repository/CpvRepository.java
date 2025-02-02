package pl.com.tenderflex.repository;

import java.util.List;
import pl.com.tenderflex.model.Cpv;

public interface CpvRepository {
     
    List<Cpv> findAll();
    
}