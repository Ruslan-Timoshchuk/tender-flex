package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.mapstract.CpvMapper;
import pl.com.tenderflex.payload.response.CpvResponse;
import pl.com.tenderflex.repository.CpvRepository;
import pl.com.tenderflex.service.CpvService;

@Service
@RequiredArgsConstructor
public class CpvServiceImpl implements CpvService {

    private final CpvRepository cpvRepository;
    private final CpvMapper cpvMapper;

    @Override
    public List<CpvResponse> getAllCpvs() {
        return cpvRepository.findAll().stream().map(cpvMapper::toResponse).toList();
    }

}