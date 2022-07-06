package com.egresso.ufma.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.ContatoEgresso;
import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.dto.ContatoDTO;
import com.egresso.ufma.model.dto.DatasDTO;
import com.egresso.ufma.model.dto.EgressoDTO;
import com.egresso.ufma.model.dto.ProfEgressoDTO;
import com.egresso.ufma.service.EgressoService;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

@RestController
@RequestMapping("/api/egressos")
public class EgressoController {
    
    @Autowired
    EgressoService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //salvar egresso
    @PostMapping("/")
    public ResponseEntity salvar(@RequestBody EgressoDTO dto) {
        Egresso egresso = Egresso.builder()
            .nome(dto.getNome())
            .cpf(dto.getCpf())
            .email(dto.getEmail())
            .senha(passwordEncoder.encode(dto.getSenha()))
            .resumo(dto.getResumo())
            .url_foto(dto.getUrl_foto())
            .build();

        try {
            Egresso salvo = service.salvar(egresso);
            return new ResponseEntity(salvo, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{idEgresso}/curso/{idCurso}")
    public ResponseEntity adicionarCurso(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idCurso") Long idCurso ,@RequestBody DatasDTO dto) {

        try {
            LocalDate dataInicio = LocalDate.parse(dto.getDataInicio());
            LocalDate dataConclusao = LocalDate.parse(dto.getDataConclusao());

            Curso adicionado = service.adicionarCurso(idEgresso, idCurso, dataInicio, dataConclusao);
            return new ResponseEntity(adicionado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{idEgresso}/curso/{idCurso}/{idNovoCurso}")
    public ResponseEntity editarCurso(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idCurso") Long idCurso, 
        @PathVariable("idNovoCurso") Long idNovoCurso, @RequestBody DatasDTO dto) {
    
        try {
            LocalDate dataInicio = LocalDate.parse(dto.getDataInicio());
            LocalDate dataConclusao = LocalDate.parse(dto.getDataConclusao());

            Curso editado = service.editarCurso(idEgresso, idCurso, idNovoCurso, dataInicio, dataConclusao);
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    @PostMapping("/{idEgresso}/cargo/{idCargo}")
    public ResponseEntity adicionarCargo(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idCargo") Long idCargo ,@RequestBody ProfEgressoDTO dto) {

        try {
            LocalDate dataRegistro = LocalDate.parse(dto.getDataRegistro());
            Cargo adicionado = service.adicionarCargo(idEgresso, idCargo, dto.getNomeEmpresa(), dto.getDescricao(), dataRegistro);
            return new ResponseEntity(adicionado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idEgresso}/cargo/{idCurso}/{idNovoCargo}")
    public ResponseEntity editarCargo(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idCargo") Long idCargo, 
        @PathVariable("idNovoCurso") Long idNovoCargo, @RequestBody ProfEgressoDTO dto) {
    
        try {
            Cargo editado = service.editarCargo(idEgresso, idCargo, idNovoCargo, dto);
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    @PostMapping("/{idEgresso}/contato/{idContato}")
    public ResponseEntity adicionarContato(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idContato") Long idContato,
        @RequestBody ContatoDTO dto) {

        try {
            ContatoEgresso editado = service.adicionarContato(idEgresso, idContato, dto.getEndereco());
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    @PutMapping("/{idEgresso}/contato/{idContato}/{idNovoContato}")
    public ResponseEntity editarContato(@PathVariable("idEgresso") Long idEgresso, @PathVariable("idContato") Long idContato,
        @PathVariable("idNovoContato") Long idNovoContato, @RequestBody ContatoDTO dto) {

        try {
            ContatoEgresso editado = service.editarContato(idEgresso, idContato, idNovoContato, dto.getEndereco());
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //deletar egresso
    //TODO: adicionar atributo de inatividade
    @PutMapping("/{idEgresso}/faixaSalario/{idCargo}/{idFaixaSalario}")
    public ResponseEntity editarFaixaSalario(@PathVariable("idEgresso") Long idEgresso,
        @PathVariable("idCargo") Long idCargo, @PathVariable("idFaixaSalario") Long idFaixaSalario) {
    
        try {
            FaixaSalario editado = service.editarFaixaSalario(idEgresso, idCargo, idFaixaSalario);
            return new ResponseEntity(editado, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

    //consultar egresso completo
    @GetMapping("/{id}") 
    public ResponseEntity obterEgresso(@PathVariable("id") Long id) {
        try {
            Egresso busca = service.getFullEgresso(id);
            return new ResponseEntity(busca, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/") 
    public ResponseEntity obterEgressos() {
        try {
            List<Egresso> busca = service.obterTodosEgressos();
            return new ResponseEntity(busca, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
