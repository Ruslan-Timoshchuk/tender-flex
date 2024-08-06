package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CpvRepository;
import pl.com.tenderflex.payload.iresponse.response.CpvResponse;
import pl.com.tenderflex.payload.mapstract.CpvMapper;
import pl.com.tenderflex.service.CpvService;

@Service
@RequiredArgsConstructor
public class CpvServiceImpl implements CpvService {

    private final CpvRepository cpvRepository;
    private final CpvMapper cpvMapper;

    @Override
    public List<CpvResponse> getAllCPVs() {
        return cpvRepository.getAllCPVs().stream().map(cpvMapper::cpvToCPVResponce).toList();
    }
}