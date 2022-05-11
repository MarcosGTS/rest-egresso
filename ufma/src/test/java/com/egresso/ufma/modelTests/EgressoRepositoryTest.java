package com.egresso.ufma.modelTests;

import java.util.Optional;

import com.egresso.ufma.model.Egresso;
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
public class EgressoRepositoryTest {
    
    @Autowired
    private EgressoRepository repository;

    @Test
    public void testeSalvarEgresso() {
        Egresso egresso = Egresso.builder()
            .cpf("000.000.000-00")
            .nome("nome teste")
            .resumo("resumo teste")
            .email("email teste")
            .url_foto("teste url-foto")
            .build();

        //Create
        Egresso salvo = repository.save(egresso);

        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<Egresso> pesquisado = repository.findById(egresso.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(egresso.getCpf(), pesquisado.get().getCpf());
        Assertions.assertEquals(egresso.getNome(), pesquisado.get().getNome());
        Assertions.assertEquals(egresso.getResumo(), pesquisado.get().getResumo());
        Assertions.assertEquals(egresso.getUrl_foto(), pesquisado.get().getUrl_foto());
        Assertions.assertEquals(egresso.getEmail(), pesquisado.get().getEmail());
    }

    @Test
    public void testeRemoverEgresso() {
        Egresso egresso = Egresso.builder()
        .cpf("000.000.000-00")
        .nome("nome teste")
        .resumo("resumo teste")
        .email("email teste")
        .url_foto("teste url-foto")
        .build();

        //Create
        Egresso salvo = repository.save(egresso);
        repository.deleteById(salvo.getId());

        Optional<Egresso> pesquisado = repository.findById(salvo.getId());

        Assertions.assertFalse(pesquisado.isPresent());
    }
}
