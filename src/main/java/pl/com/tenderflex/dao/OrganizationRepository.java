package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.Organization;

public interface OrganizationRepository {

    Organization create(Organization organization);
    
}