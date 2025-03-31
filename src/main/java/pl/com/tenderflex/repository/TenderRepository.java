package pl.com.tenderflex.repository;

import java.util.List;
import pl.com.tenderflex.model.Tender;

public interface TenderRepository {
    
    Tender save(Tender tender);

    List<Tender> findWithPagination(Integer amountTenders, Integer amountTendersToSkip);
    
    List<Tender> findByContractorWithPagination(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip);

    Integer countTendersByContractor(Integer userId); 
    
    Integer countTenders();

    Tender findById(Integer tenderId);

}