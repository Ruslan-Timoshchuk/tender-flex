package pl.com.tenderflex.service;

import java.util.Set;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.EContractStatus;
import pl.com.tenderflex.payload.response.ContractResponse;

public interface ContractService {

    Contract save(Contract contract, Tender tender);

    Contract findById(Integer id);
    
    ContractResponse findDetailsById(Integer id);

    Contract sign(Contract contract);

    Contract initiateContractSigning(Contract contract, Offer offer);

    Contract decline(Contract contract);

    boolean hasOffer(Contract contract);

    void handleOnSigningDeadlinePassed(Contract contract);

    Set<Contract> findAll(EContractStatus globalStatus);
 
}