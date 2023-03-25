package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.CPV;

public interface CPVrepository {

    List<CPV> getAllCPVs();
    
}