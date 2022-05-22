package com.egresso.ufma.service;

import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CursoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    
    @Autowired
    CursoRepository repository;

    public List<Egresso> consultarEgressos(Curso curso) {
        return repository.getEgressos(curso.getId());
    }

    public Integer consultarQuantidadeEgressos(Curso curso) {
        return repository.getNumberEgressos(curso.getId());
    }
}
