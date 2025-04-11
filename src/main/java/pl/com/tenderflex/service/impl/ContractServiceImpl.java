package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
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
    @Transactional
    public Contract create(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public ContractResponse findById(Integer id) {
        Contract contract = contractRepository.findById(id);
        Boolean hasOffer = false;
        if (contract.getOffer().getId() != null) {
            hasOffer = true;
        }
        return contractMapper.toResponse(contractRepository.findById(id), hasOffer);
    }
    
}