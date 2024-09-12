package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Tender;

public interface TenderStatusService {

    Tender assignNewUserStatus(Tender tender);
    
}