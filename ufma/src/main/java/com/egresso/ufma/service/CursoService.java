package com.egresso.ufma.service;

import java.util.LinkedList;
import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CursoEgressoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    
    @Autowired
    CursoRepository repository;
    
    @Autowired
    CursoEgressoRepository cursoEgressoRepo;

    @Autowired
    EgressoRepository egressoRepo;

    public List<Egresso> consultarEgressos(Long cursoId) {
        verificarCurso(cursoId);

        Curso curso = repository.findCompleteCurso(cursoId);
        List<Egresso> egressos = new LinkedList<Egresso>();
        List<CursoEgresso> cursos = curso.getCursoEgressoAssoc();
        
        for (CursoEgresso cursoEgresso : cursos) {
            Long crrEgressoId = cursoEgresso.getId().getEgresso_id();
            Long crrCursoId = cursoEgresso.getId().getCurso_id();

            if (crrCursoId == cursoId) {
                Egresso relacionado = egressoRepo.findById(crrEgressoId).get();
                egressos.add(relacionado);
            }
        }

        return egressos;
    }

    public List<Curso> obterCursos() {
        return repository.findAll();
    }

    public Integer consultarQuantidadeEgressos(Long cursoId) {
        return consultarEgressos(cursoId).size();
    }

    public void verificarCurso(Long cursoId) {
        if (!repository.existsById(cursoId))
            throw new RegraNegocioRunTime("Curso informado nao existe");

        Curso curso = repository.findById(cursoId).get();

        if (curso == null)
            throw new RegraNegocioRunTime("Curso valido deve ser informado");
        if (curso.getNome() == null || curso.getNome().isBlank())
            throw new RegraNegocioRunTime("Curso deve possuir um nome");
        if (curso.getNivel() == null || curso.getNivel().isBlank())
            throw new RegraNegocioRunTime("Curso deve possuir um nivel");
    }
}
