package pl.com.tenderflex.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TypeOfTenderRepository;
import pl.com.tenderflex.payload.iresponse.response.TypeOfTenderResponse;
import pl.com.tenderflex.payload.mapstract.TypeOfTenderMapper;
import pl.com.tenderflex.service.TypeOfTenderService;

@Service
@RequiredArgsConstructor
public class TypeOfTenderServiceImpl implements TypeOfTenderService {

    private final TypeOfTenderMapper typeOfTenderMapper;
    private final TypeOfTenderRepository tenderTypeRepository;

    @Override
    public List<TypeOfTenderResponse> getAll() {
        return tenderTypeRepository.getAll().stream().map(typeOfTenderMapper::typeOfTenderToTypeOfTenderResponse)
                .toList();
    }
}