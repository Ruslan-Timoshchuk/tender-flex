package com.flex.tender.service.impl;

import static java.time.LocalDate.*;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.payload.mapper.ContractMapper;
import com.flex.tender.payload.request.ContractRequest;
import com.flex.tender.payload.response.ContractResponse;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.service.ContractService;
import com.flex.tender.service.ContractTypeService;
import com.flex.tender.service.CurrencyService;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.details.TenderDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final TenderDetailsService tenderDetailsService;
    private final ContractTypeService contractTypeService;
    private final CurrencyService currencyService;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public ContractResponse save(ContractRequest contractRequest) {
        Contract contract = contractMapper.toEntity(contractRequest);
        contract.setTender(tenderDetailsService.findById(contractRequest.tenderId()));
        contract.setContractType(contractTypeService.findById(contractRequest.contractTypeId()));
        contract.setCurrency(currencyService.findById(contractRequest.currencyId()));
        contract.setFileMetadata(fileStorageService.findById(contractRequest.fileMetadataId()));
        contract.setGlobalStatus(EContractStatus.DRAFT);
        return contractMapper.toResponse(contractRepository.save(contract));
    }

    @Override
    public Contract findById(Integer id) {
        return contractRepository.findById(id);
    }

    @Override
    public ContractResponse findDetailsById(Integer id) {
        Contract contract = contractRepository.findById(id);
        return contractMapper.toResponse(contract);
    }

    @Override
    public Contract initiateContractSigning(Contract contract, Offer offer) {
        contract.setOffer(offer);
        contract.setGlobalStatus(EContractStatus.PENDING_SIGNATURE);
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
        contract.setOffer(Offer
                            .builder()
                            .build());
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
    
    @Override
    public boolean hasOffer(Contract contract) {
        return contract.getOffer() != null && contract.getOffer().getId() != null;
    }

    @Override
    public Set<Contract> findAll(EContractStatus globalStatus) {
        return contractRepository.findAll(globalStatus);
    }

}