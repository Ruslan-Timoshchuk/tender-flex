package pl.com.tenderflex.currency.payload;

import pl.com.tenderflex.currency.model.Currency;

public record CurrencyResponse(Integer id, String type) { 
    
    public CurrencyResponse(Currency currency) {
        this(currency.getId(), currency.getCurrencyType());
    }
    
}