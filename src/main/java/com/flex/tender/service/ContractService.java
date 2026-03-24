package com.flex.tender.service;

import java.util.Set;

import com.flex.tender.model.Contract;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.payload.response.ContractResponse;

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