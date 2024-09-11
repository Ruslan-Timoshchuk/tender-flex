package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.TenderFile;

public interface TenderFileRepository {

    TenderFile create(TenderFile file);
    
}