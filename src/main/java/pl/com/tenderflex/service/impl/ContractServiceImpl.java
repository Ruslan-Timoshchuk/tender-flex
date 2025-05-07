package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
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
        return contractRepository.save(contract);
    }

    @Override
    public Contract findById(Integer id) {
        return contractRepository.findById(id);
    }

    @Override
    public ContractResponse findDetailsById(Integer id) {
        Contract contract = contractRepository.findById(id);
        return contractMapper.toResponse(contract, hasOffer(contract));
    }

    @Override
    public void update(Contract contract) {
        contractRepository.update(contract);
    }

    @Override
    public Contract initiateContractSigning(Contract contract, Offer offer) {
        contract.setOffer(offer);
        contractRepository.update(contract);
        return contract;
    }

    @Override
    public Contract sign(Contract contract) {
        contract.setHasSigned(true);
        contract.setSignedDate(now());
        contractRepository.update(contract);
        return contract;
    }
    
    @Override
    public Contract decline(Contract contract) {
        contract.setOffer(Offer
                            .builder()
                            .build());
        contractRepository.update(contract);
        return contract;
    }

    private Boolean hasOffer(Contract contract) {
        return contract.getOffer() != null && contract.getOffer().getId() != null;
    }

}