package com.flex.tender.payload.response;

public record CountryResponse(
        Integer id, 
        String name,
        String isoCode,
        String phoneCode) {
}