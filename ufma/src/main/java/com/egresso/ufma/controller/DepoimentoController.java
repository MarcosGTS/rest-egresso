package com.egresso.ufma.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.dto.DepoimentoDTO;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.service.DepoimentoService;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/depoimentos")
public class DepoimentoController {
    
    @Autowired
    DepoimentoService service;

    @Autowired
    EgressoRepository egressoRepo;

    @PostMapping("/egresso/{egressoId}")
    public ResponseEntity salvar(@PathVariable("egressoId") Long egressoId, @RequestBody DepoimentoDTO dto) {
        Egresso egresso = egressoRepo.findById(egressoId).get();
        //Validar egresso

        Depoimento depoimento = Depoimento.builder()
            .data(LocalDate.now())
            .texto(dto.getTexto())
            .egresso(egresso)
            .build();

        try {
            Depoimento salvo = service.salvar(depoimento);
            return new ResponseEntity(salvo, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity editar(@PathVariable("id") Long id, @RequestBody DepoimentoDTO dto) {

        try {
            Depoimento editado = service.editar(id, dto.getTexto());
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
    
        try {
            Depoimento editado = service.remover(id);
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity obterDepoimentos() {
        
        try {
            List<Depoimento> depoimentos = service.consultar();
            return new ResponseEntity(depoimentos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/egresso/{id}")
    public ResponseEntity depoimentoEgresso(@PathVariable("id") Long id) {
        
        try {
            List<Depoimento> depoimento = service.consultarPorEgresso(id);
            return new ResponseEntity(depoimento, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
