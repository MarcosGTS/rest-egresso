package com.egresso.ufma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.model.Contato;
import com.egresso.ufma.repository.ContatoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/contatos/")
public class ContatoController {
    @Autowired
    ContatoRepository repo;

    @GetMapping("/")
    public ResponseEntity obterEgressos() {
        
        try {
            List <Contato> egressos = repo.findAll();
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
