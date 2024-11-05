package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.ContractType;

public interface ContractTypeRepository {

    List<ContractType> getAll();
    
}