package pl.com.tenderflex.dao;

import java.util.Set;

import pl.com.tenderflex.model.Cpv;

public interface CpvRepository {

    Set<Cpv> getAllCpvs();
    
}