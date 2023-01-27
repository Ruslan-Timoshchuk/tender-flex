package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.Tender;

public interface TenderRepository {

    Tender create(Tender tender, Integer contractorId);
    
}