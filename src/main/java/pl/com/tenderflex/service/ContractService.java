package pl.com.tenderflex.service;

import java.util.Set;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.response.ContractResponse;

public interface ContractService {

    Contract save(Contract contract, Tender tender);

    Contract findById(Integer id);
    
    ContractResponse findDetailsById(Integer id);

    void update(Contract contract);

    Contract sign(Contract contract);

    Contract initiateContractSigning(Contract contract, Offer offer);

    Contract decline(Contract contract);

    boolean hasOffer(Contract contract);

    Set<Contract> findAll(boolean hasSigned);

    void handleOnSigningDeadlinePassed(Contract contract);
 
}