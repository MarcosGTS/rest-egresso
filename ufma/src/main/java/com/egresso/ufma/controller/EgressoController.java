package com.egresso.ufma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.service.EgressoService;

@RestController
@RequestMapping("/api/egresso")
public class EgressoController {
    
    @Autowired
    EgressoService service;

    
}
