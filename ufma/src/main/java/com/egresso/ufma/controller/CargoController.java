package com.egresso.ufma.controller;

import java.util.List;

import javax.persistence.Id;

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

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.dto.CargoDTO;
import com.egresso.ufma.service.CargoService;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/cargos")
public class CargoController {
    @Autowired
    CargoService service;

    @PostMapping
    public ResponseEntity salvar(@RequestBody CargoDTO dto) {
        Cargo cargo = Cargo.builder()
            .nome(dto.getNome())
            .descricao(dto.getDescricao())
            .build();
        
        try {
            Cargo salvo = service.salvar(cargo);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity editar(@PathVariable("id") Long id, @RequestBody CargoDTO dto) {

        try {
            Cargo editado = service.editar(id, dto.getNome(), dto.getDescricao());
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        
        try {
            Cargo deletado = service.deletar(id);
            return new ResponseEntity(deletado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity obterQuantitativo(@PathVariable("id") Long id) {
        try {
            Integer quantidade = service.consultarQuantidadeEgressos(id);
            return new ResponseEntity(quantidade, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/egresso/{id}")
    public ResponseEntity obterCargo(@PathVariable("id") Long id) {

        try {
            List<Cargo> cargos = service.consultarCargoPorEgresso(id);
            return new ResponseEntity(cargos, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
