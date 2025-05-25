package pl.com.tenderflex.repository;

import java.time.LocalDate;
import java.util.Set;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ETenderStatus;

public interface TenderRepository {
    
    Tender save(Tender tender);

    Set<Tender> findWithPagination(Integer amountTenders, Integer amountTendersToSkip);
    
    Set<Tender> findByContractorWithPagination(Integer contractorId, Integer amountTenders, Integer amountTendersToSkip);

    Integer countTendersByContractor(Integer userId); 
    
    Integer countTenders();

    Tender findById(Integer tenderId);

    void update(Tender tender);

    Set<Tender> findActiveWhereSubmissionIsExpired(ETenderStatus status, LocalDate currentDate);

}