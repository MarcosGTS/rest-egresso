package com.egresso.ufma.modelTests;

import java.util.Optional;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.repository.CursoRepository;

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
public class CursoRepositoryTest {
    
    @Autowired
    private CursoRepository repository;

    @Test
    public void testeSalvarCurso() {
        Curso curso = Curso.builder()
        .nome("teste")
        .nivel("Superior")
        .build();

        //Create
        Curso salvo = repository.save(curso);

        Assertions.assertNotNull(salvo);

        //Recover
        Optional<Curso> pesquisado = repository.findById(curso.getId());

        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(curso.getNivel(), pesquisado.get().getNivel());
        Assertions.assertEquals(curso.getNome(), pesquisado.get().getNome());
    }

    @Test
    public void testeRemoverCurso() {
        Curso curso = Curso.builder()
        .nome("teste")
        .nivel("Superior")
        .build();

        Curso salvo = repository.save(curso); 
        repository.deleteById(salvo.getId());

        Optional<Curso> pesquisado = repository.findById(salvo.getId());

        Assertions.assertFalse(pesquisado.isPresent());
    }

}
