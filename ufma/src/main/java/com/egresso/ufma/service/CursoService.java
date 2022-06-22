package com.egresso.ufma.service;

import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    
    @Autowired
    CursoRepository repository;

    public List<Egresso> consultarEgressos(Long cursoId) {
        return repository.getEgressos(cursoId);
    }

    public Integer consultarQuantidadeEgressos(Long cursoId) {
        return consultarEgressos(cursoId).size();
    }

    public void verificarCurso(Curso curso) {
        if (curso == null)
            throw new RegraNegocioRunTime("Curso valido deve ser informado");
        if (curso.getNome() == null || curso.getNome().isBlank())
            throw new RegraNegocioRunTime("Curso deve possuir um nome");
        if (curso.getNivel() == null || curso.getNivel().isBlank())
            throw new RegraNegocioRunTime("Curso deve possuir um nivel");
    }
}
