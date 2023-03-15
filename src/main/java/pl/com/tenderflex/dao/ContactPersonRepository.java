package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.ContactPerson;

public interface ContactPersonRepository {

    ContactPerson create(ContactPerson contactPerson);
    
}