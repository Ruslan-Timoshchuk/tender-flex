package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.EContractStatus;
import pl.com.tenderflex.payload.mapstract.ContractMapper;
import pl.com.tenderflex.payload.response.ContractResponse;
import pl.com.tenderflex.repository.ContractRepository;
import pl.com.tenderflex.service.ContractService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    @Override
    public Contract save(Contract contract, Tender tender) {
        contract.setTender(tender);
        contract.setGlobalStatus(EContractStatus.DRAFT);
        return contractRepository.save(contract);
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