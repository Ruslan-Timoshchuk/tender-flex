package com.flex.tender.service.write.impl;

import static java.time.LocalDate.*;
import static com.flex.tender.model.enumeration.EContractStatus.*;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.payload.mapper.ContractMapper;
import com.flex.tender.payload.request.ContractRequest;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.service.read.ContractTypeDetailsService;
import com.flex.tender.service.read.CurrencyDetailsService;
import com.flex.tender.service.read.FileStorageDetailsService;
import com.flex.tender.service.write.ContractService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final ContractTypeDetailsService contractTypeDetailsService;
    private final CurrencyDetailsService currencyDetailsService;
    private final FileStorageDetailsService fileStorageDetailsService;
    
    @Override 
    public Contract buildEntity(ContractRequest contractRequest) {
        Contract contract = contractMapper.toEntity(contractRequest);
        contract.setContractType(contractTypeDetailsService.findById(contractRequest.contractTypeId()));
        contract.setCurrency(currencyDetailsService.findById(contractRequest.currencyId()));
        contract.setFileMetadata(fileStorageDetailsService.findById(contractRequest.fileMetadataId()));
        contract.setGlobalStatus(DRAFT);
        return contract;
    }

    @Override
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }
    
    @Override
    public Contract initiateContractSigning(Contract contract) {
        contract.setGlobalStatus(PENDING_SIGNATURE);
        contractRepository.update(contract);
        return contract;
    }

    @Override
    public Contract sign(Contract contract) {
        contract.setGlobalStatus(EContractStatus.SIGNED);
        contract.setSignedDate(now());
        contractRepository.update(contract);
        return contract;
    }
    
    @Override
    public Contract decline(Contract contract) {
        contract.setGlobalStatus(EContractStatus.DRAFT);
        contractRepository.update(contract);
        return contract;
    }

    @Override
    public void handleOnSigningDeadlinePassed(Contract contract) {
        LocalDate extendedSignedDeadline = contract.getSignedDeadline().plusDays(7);
        contract.setSignedDeadline(extendedSignedDeadline);
        decline(contract);
    }

}