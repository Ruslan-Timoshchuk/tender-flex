package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CPVrepository;
import pl.com.tenderflex.payload.mapstract.CPVMapper;
import pl.com.tenderflex.payload.response.CPVresponse;
import pl.com.tenderflex.service.CPVService;

@Service
@RequiredArgsConstructor
public class CPVServiceImpl implements CPVService {

    private final CPVrepository cpvRepository;
    private final CPVMapper cpvMapper;

    @Override
    public List<CPVresponse> getAllCPVs() {
        return cpvRepository.getAllCPVs().stream().map(cpvMapper::cpvToCPVResponce).toList();
    }
}