package com.egresso.ufma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.service.CursoService;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    
    @Autowired
    CursoService service;

    @GetMapping("/") 
    public ResponseEntity obterCursos() {
        try {
            List<Curso> cursos = service.obterCursos();
            return new ResponseEntity(cursos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity obterQuantitavivo(@PathVariable("id") Long id) {
        
        try {
            Integer egressos = service.consultarQuantidadeEgressos(id);
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/egressos/{id}")
    public ResponseEntity obterEgressos(@PathVariable("id") Long id) {
        
        try {
            List<Egresso> egressos = service.consultarEgressos(id);
            return new ResponseEntity(egressos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
