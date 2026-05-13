package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.Currency;

public interface CurrencyRepository {

    List<Currency> findAll();
    
}