package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContractTypeRepository;
import pl.com.tenderflex.payload.iresponse.response.ContractTypeResponse;
import pl.com.tenderflex.payload.mapstract.ContractTypeMapper;
import pl.com.tenderflex.service.ContractTypeService;

@Service
@RequiredArgsConstructor
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeMapper contractTypeMapper;
    private final ContractTypeRepository contractTypeRepository;

    @Override
    public List<ContractTypeResponse> getAll() {
        return contractTypeRepository.getAll().stream().map(contractTypeMapper::typeOfTenderToTypeOfTenderResponse)
                .toList();
    }
}