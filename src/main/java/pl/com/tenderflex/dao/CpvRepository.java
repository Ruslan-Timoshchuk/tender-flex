package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Cpv;

public interface CpvRepository {

    List<Cpv> getAllCPVs();
    
}