package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.repository.ContractRepository;
import pl.com.tenderflex.service.ContractService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    @Override
    @Transactional
    public Contract create(Contract contract) {
        return contractRepository.save(contract);
    }

}