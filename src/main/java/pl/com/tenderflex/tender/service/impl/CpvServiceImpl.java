package pl.com.tenderflex.tender.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.tender.payload.CpvResponse;
import pl.com.tenderflex.tender.repository.CpvRepository;
import pl.com.tenderflex.tender.service.CpvService;

@Service
@RequiredArgsConstructor
public class CpvServiceImpl implements CpvService {

    private final CpvRepository cpvRepository;

    @Override
    public List<CpvResponse> getAllCpvs() {
        return cpvRepository.findAll().stream().map(CpvResponse::new).toList();
    }

    @Override
    public CpvResponse getById(Integer id) {
        return cpvRepository.findById(id)
                .map(CpvResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Cpv not found with id: " + id));
    }

}