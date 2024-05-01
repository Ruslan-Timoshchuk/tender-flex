package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.payload.response.TypeOfTenderResponse;

public interface TypeOfTenderService {

    List<TypeOfTenderResponse>getAll();
    
}