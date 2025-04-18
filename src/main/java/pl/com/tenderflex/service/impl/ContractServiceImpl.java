package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.payload.mapstract.ContractMapper;
import pl.com.tenderflex.payload.request.InitiateContractSigningRequest;
import pl.com.tenderflex.payload.response.ContractResponse;
import pl.com.tenderflex.repository.ContractRepository;
import pl.com.tenderflex.service.ContractService;
import pl.com.tenderflex.service.OfferService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final OfferService offerService;
    private final ContractMapper contractMapper;

    @Override
    public Contract save(Contract contract) {
        contract.setHasSigned(false);
        return contractRepository.save(contract);
    }

    @Override
    public ContractResponse findById(Integer id) {
        Contract contract = contractRepository.findById(id);
        return contractMapper.toResponse(contract, hasOffer(contract));
    }

    @Override
    @Transactional
    public ContractResponse initiateContractSigning(InitiateContractSigningRequest contractSigningRequest) {
        Contract contract = contractRepository.findById(contractSigningRequest.contractId());
        contract.setOffer(
                offerService.selectWinningOffer(contractSigningRequest.offerId(), contractSigningRequest.awardId()));
        contractRepository.update(contract);
        return contractMapper.toResponse(contract, hasOffer(contract));
    }

    private Boolean hasOffer(Contract contract) {
        return contract.getOffer() != null && contract.getOffer().getId() != null;
    }

}