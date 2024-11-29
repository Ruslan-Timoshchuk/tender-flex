package pl.com.tenderflex.currency.repository;

import java.util.List;

import pl.com.tenderflex.currency.model.Currency;

public interface CurrencyRepository {

    List<Currency> findAll();
    
}