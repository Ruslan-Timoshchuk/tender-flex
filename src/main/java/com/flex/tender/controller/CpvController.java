package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.response.CpvResponse;
import com.flex.tender.service.read.CpvService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.cpvs.path}")
public class CpvController {
    
    private final CpvService cpvService;
    
    @Secured(CONTRACTOR)
    @GetMapping
    public ResponseEntity<List<CpvResponse>> findAll() {
        return ResponseEntity
                   .ok(cpvService.findAll());
    }
    
}