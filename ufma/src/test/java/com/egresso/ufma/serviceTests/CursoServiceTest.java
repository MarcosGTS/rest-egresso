package com.egresso.ufma.serviceTests;

import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CursoEgressoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.service.CursoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CursoServiceTest {
    
    @Autowired
    CursoService service;

    @Autowired
    CursoRepository repository;

    @Autowired 
    EgressoRepository egressoRepo;

    @Autowired
    CursoEgressoRepository cursoEgressoRepo;

    @Test
    public void deveConsultarEgressosPorCurso() {
        Curso novoCurso = repository.save(Curso
        .builder()
        .nome("Engenharia da computacao")
        .nivel("Mestrado")
        .build());

        Egresso egresso1 = egressoRepo.save(Egresso
        .builder()
        .nome("Jorel")
        .build()); 

        Egresso egresso2 = egressoRepo.save(Egresso
        .builder()
        .nome("Mario")
        .build()); 

        cursoEgressoRepo.save(CursoEgresso
        .builder()
        .id(new CursoEgressoPk(novoCurso.getId(), egresso1.getId()))
        .curso(novoCurso)
        .egresso(egresso1)
        .build());

        cursoEgressoRepo.save(CursoEgresso
        .builder()
        .id(new CursoEgressoPk(novoCurso.getId(), egresso2.getId()))
        .curso(novoCurso)
        .egresso(egresso2)
        .build());

        List<Egresso> consulta = service.consultarEgressos(novoCurso.getId());

        Assertions.assertEquals(2, consulta.size());
        Assertions.assertEquals(egresso1.getNome(), consulta.get(0).getNome());
        Assertions.assertNotEquals(egresso1.getNome(), consulta.get(1).getNome());
    }

    @Test
    public void deveConsultarQuantidadeEgressos() {
        Curso novoCurso = repository.save(Curso
        .builder()
        .nome("Engenharia da computacao")
        .nivel("Mestrado")
        .build());

        Egresso egresso1 = egressoRepo.save(Egresso
        .builder()
        .nome("Jorel")
        .build()); 

        Egresso egresso2 = egressoRepo.save(Egresso
        .builder()
        .nome("Mario")
        .build()); 

        cursoEgressoRepo.save(CursoEgresso
        .builder()
        .id(new CursoEgressoPk(novoCurso.getId(), egresso1.getId()))
        .curso(novoCurso)
        .egresso(egresso1)
        .build());

        cursoEgressoRepo.save(CursoEgresso
        .builder()
        .id(new CursoEgressoPk(novoCurso.getId(), egresso2.getId()))
        .curso(novoCurso)
        .egresso(egresso2)
        .build());

        Integer consulta = service.consultarQuantidadeEgressos(novoCurso.getId());

        Assertions.assertEquals(2, consulta);
    }

}
