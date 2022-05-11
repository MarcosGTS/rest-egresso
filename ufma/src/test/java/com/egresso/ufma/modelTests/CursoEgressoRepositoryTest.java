package com.egresso.ufma.modelTests;

import java.time.LocalDate;
import java.util.Optional;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CursoEgressoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;

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
public class CursoEgressoRepositoryTest {
    
    @Autowired
    private CursoEgressoRepository repository;
    @Autowired
    private EgressoRepository egressoRepo;
    @Autowired
    private CursoRepository cursoRepo;

    @Test
    public void testeSalvarCursoEgresso() {
        Curso cursoSalvo = cursoRepo.save(Curso.builder()
            .nome("nome curso")
            .nivel("avancado")
            .build()
        );

        Egresso egressoSalvo = egressoRepo.save(Egresso.builder()
            .email("teste")
            .build()
        );
        
        CursoEgressoPk idCursoEgresso = new CursoEgressoPk(
            egressoSalvo.getId(),
            cursoSalvo.getId() 
        );
    
        CursoEgresso cursoEgresso = CursoEgresso.builder()
            .id(idCursoEgresso)
            .curso(cursoSalvo)
            .egresso(egressoSalvo)
            .data_inicio(LocalDate.now())
            .data_conclusao(LocalDate.now())
            .build();

        //Create
        
        CursoEgresso salvo = repository.save(cursoEgresso);
        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<CursoEgresso> pesquisado = repository.findById(cursoEgresso.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(cursoEgresso.getData_inicio(), pesquisado.get().getData_inicio());
        Assertions.assertEquals(cursoEgresso.getData_conclusao(), pesquisado.get().getData_conclusao());
    }

    @Test
    public void testeRemoverCursoEgresso() {
        Curso cursoSalvo = cursoRepo.save(Curso.builder()
            .nome("nome curso")
            .nivel("avancado")
            .build()
        );

        Egresso egressoSalvo = egressoRepo.save(Egresso.builder()
            .email("teste")
            .build()
        );
        
        CursoEgressoPk idCursoEgresso = new CursoEgressoPk(
            egressoSalvo.getId(),
            cursoSalvo.getId() 
        );

        CursoEgresso cursoEgresso = CursoEgresso.builder()
            .id(idCursoEgresso)
            .curso(cursoSalvo)
            .egresso(egressoSalvo)
            .data_inicio(LocalDate.now())
            .data_conclusao(LocalDate.now())
            .build();

        //Create
        
        CursoEgresso salvo = repository.save(cursoEgresso);
        repository.deleteById(salvo.getId());

        Optional<CursoEgresso> pesquisado = repository.findById(salvo.getId());

        Assertions.assertFalse(pesquisado.isPresent());
    }
}
