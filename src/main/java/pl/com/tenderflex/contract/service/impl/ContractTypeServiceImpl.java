package pl.com.tenderflex.contract.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.contract.payload.ContractTypeMapper;
import pl.com.tenderflex.contract.payload.ContractTypeResponse;
import pl.com.tenderflex.contract.repository.ContractTypeRepository;
import pl.com.tenderflex.contract.service.ContractTypeService;

@Service
@RequiredArgsConstructor
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeRepository contractTypeRepository;

    @Override
    public List<ContractTypeResponse> getAll() {
        return contractTypeRepository.getAll().stream().map(contractTypeMapper::teResponse)
                .toList();
    }
}