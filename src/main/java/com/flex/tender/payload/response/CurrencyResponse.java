package com.flex.tender.payload.response;

public record CurrencyResponse(
        Integer id, 
        String code,
        String symbol) {   
}