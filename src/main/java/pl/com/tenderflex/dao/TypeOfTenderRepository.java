package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.TypeOfTender;

public interface TypeOfTenderRepository {

    List<TypeOfTender> getAll();
    
}