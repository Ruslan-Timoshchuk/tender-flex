package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.TenderStatus;

public interface TenderStatusRepository {

    TenderStatus create(TenderStatus status);
    
}