package pl.com.tenderflex.contract.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.contract.payload.ContractMapper;
import pl.com.tenderflex.contract.payload.ContractRequest;
import pl.com.tenderflex.contract.payload.ContractResponse;
import pl.com.tenderflex.contract.repository.ContractRepository;
import pl.com.tenderflex.contract.service.ContractService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;

    @Override
    @Transactional
    public ContractResponse create(ContractRequest contractRequest) {
        return contractMapper.toResponse(contractRepository.save(contractMapper.toEntity(contractRequest)));
    }

    @Override
    public ContractResponse findByTenderId(Integer id) {
        return contractMapper.toResponse(contractRepository.findByTenderId(id));
    }

}