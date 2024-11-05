package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContractRepository;
import pl.com.tenderflex.payload.iresponse.response.ContractResponse;
import pl.com.tenderflex.payload.mapstract.ContractMapper;
import pl.com.tenderflex.payload.request.ContractRequest;
import pl.com.tenderflex.service.ContractService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;

    @Override
    @Transactional
    public ContractResponse create(ContractRequest contractRequest) {
        return contractMapper.contractToContractResponse(
                contractRepository.save(contractMapper.contractRequestToContract(contractRequest)));
    }

}