package pl.com.tenderflex.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.tenderflex.dao.TenderDao;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Tender;

@Service
public class TenderService {

    private final TenderDao tenderDao;
    
    public TenderService(TenderDao tenderDao) {
        this.tenderDao = tenderDao;
    }
    
    @Transactional
    public void createTender(Tender tender) {
        try {
        tenderDao.create(tender);
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when saving the tender", e);
        } 
    }
}