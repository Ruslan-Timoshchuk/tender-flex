package pl.com.tenderflex.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.response.TypeOfTenderResponse;
import pl.com.tenderflex.service.TypeOfTenderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/type_of_tender")
public class TypeOfTenderController {

    private final TypeOfTenderService typeOfTenderService;
    
    @Secured("CONTRACTOR")
    @GetMapping("/list")
    public List<TypeOfTenderResponse> getAllTypes() {
        return typeOfTenderService.getAll();
    }
}