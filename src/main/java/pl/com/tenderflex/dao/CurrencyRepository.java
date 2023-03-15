package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Currency;

public interface CurrencyRepository {

    List<Currency> getAll();
    
}