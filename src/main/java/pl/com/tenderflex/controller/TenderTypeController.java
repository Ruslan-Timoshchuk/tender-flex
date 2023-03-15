package pl.com.tenderflex.controller;

import static java.util.Arrays.asList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.TenderType;

@RestController
@RequestMapping("/api/v1/tender_type")
public class TenderTypeController {

    @GetMapping("/list")
    public List<TenderType> getTenderTypes() {
        return asList(TenderType.values());
    }
}