package com.egresso.ufma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.dto.EstatisticaDTO;
import com.egresso.ufma.service.FaixaSalarioService;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/faixasalario")
public class FaixaSalarioController {
    
    @Autowired
    FaixaSalarioService service;

    @GetMapping("/estatisticas")
    public ResponseEntity quantitativoEgresso() {

        try {
            List<EstatisticaDTO> consulta = service.obterQuantitavioCompleto();
            return new ResponseEntity(consulta, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    public ResponseEntity obterFaixasSalariais() {

        try {
            List <FaixaSalario> cargos = service.obterFaixasSalariais();
            return new ResponseEntity(cargos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
