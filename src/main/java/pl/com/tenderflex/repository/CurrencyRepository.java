package pl.com.tenderflex.repository;

import java.util.List;

import pl.com.tenderflex.model.Currency;

public interface CurrencyRepository {

    List<Currency> findAll();
    
}