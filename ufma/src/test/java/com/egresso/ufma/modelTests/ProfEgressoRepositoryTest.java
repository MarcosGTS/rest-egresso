package com.egresso.ufma.modelTests;

import java.time.LocalDate;
import java.util.Optional;

import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.ProfEgressoRepository;

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
public class ProfEgressoRepositoryTest {
    @Autowired
    private ProfEgressoRepository repository;

    @Test
    public void testeCrudProfEgresso() {
        ProfEgresso profissao = ProfEgresso.builder()
        .descricao("teste descricao")
        .empresa("empresa teste")
        .data_registro(LocalDate.now())
        .build();

        //Create
        ProfEgresso salvo = repository.save(profissao);

        Assertions.assertNotNull(salvo);

        //Recover
        Optional<ProfEgresso> pesquisado = repository.findById(profissao.getId());

        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(profissao.getDescricao(), pesquisado.get().getDescricao());
        Assertions.assertEquals(profissao.getEmpresa(), pesquisado.get().getEmpresa());
        Assertions.assertEquals(profissao.getData_registro(), pesquisado.get().getData_registro());
       
        //Delete
        repository.delete(profissao);
    }
}
