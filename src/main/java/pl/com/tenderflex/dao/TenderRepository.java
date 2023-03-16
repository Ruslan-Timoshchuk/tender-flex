package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Tender;

public interface TenderRepository {

    Tender create(Tender tender, Integer contractorId);

    List<Tender> getByContractor(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip);

    Integer countTendersByContractor(Integer contractorId);
    
    List<Tender> getAll(Integer amountTenders, Integer amountTendersToSkip);
    
    Integer countAllTenders();

    Tender getById(Integer tenderId);

}