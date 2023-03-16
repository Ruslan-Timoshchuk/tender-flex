package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Country;

public interface CountryRepository {

    List<Country> getAll();
    
}