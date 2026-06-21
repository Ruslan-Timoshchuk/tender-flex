package com.flex.tender.service.write;

import java.util.Set;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.payload.request.ContractRequest;
import com.flex.tender.payload.response.ContractResponse;

public interface ContractService {

    Contract save(Contract contract);
    
    Contract findById(Integer id);
    
    ContractResponse findDetailsById(Integer id);

    Contract sign(Contract contract);

    Contract initiateContractSigning(Contract contract);

    Contract decline(Contract contract);

    void handleOnSigningDeadlinePassed(Contract contract);

    Set<Contract> findAll(EContractStatus globalStatus);

    Contract buildEntity(ContractRequest contractRequest);

}