package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.response.CountryResponse;
import com.flex.tender.service.read.CountryDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.countries.path}")
public class CountryController {

    private final CountryDetailsService countryDetailsService;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping
    public ResponseEntity<List<CountryResponse>> findAll() {
        return ResponseEntity
                   .ok(countryDetailsService.findAll());
    }

}