package pl.com.tenderflex.service;

import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.response.ContractResponse;

public interface ContractService {

    Contract save(Contract contract, Tender tender);

    Contract findById(Integer id);
    
    ContractResponse findDetailsById(Integer id);

    void update(Contract contract);

    Contract signContract(Contract contract);

    Contract initiateContractSigning(Contract contract, Offer offer);
 
}