package pl.com.tenderflex.companyprofile.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileRequest;
import pl.com.tenderflex.companyprofile.payload.CompanyProfileResponse;
import pl.com.tenderflex.companyprofile.service.CompanyProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companyprofiles")
public class CompanyProfileController {

    private final CompanyProfileService companyProfileService;
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @PostMapping
    public CompanyProfileResponse create(@RequestBody CompanyProfileRequest companyProfile) {
        return companyProfileService.create(companyProfile);
    }
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping("/{id}")
    public CompanyProfileResponse findById(@PathVariable Integer id) {
        return companyProfileService.findById(id);
    }
    
}