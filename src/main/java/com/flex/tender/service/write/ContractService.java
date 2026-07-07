package com.flex.tender.service.write;

import com.flex.tender.model.Contract;
import com.flex.tender.payload.request.ContractRequest;

public interface ContractService {

    Contract save(Contract contract);

    Contract sign(Contract contract);

    Contract initiateContractSigning(Contract contract);

    Contract decline(Contract contract);

    void handleOnSigningDeadlinePassed(Contract contract);

    Contract buildEntity(ContractRequest contractRequest);

}