package com.egresso.ufma.modelTests;

import java.util.Optional;

import com.egresso.ufma.model.Contato;
import com.egresso.ufma.repository.ContatoRepository;

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
public class ContatoRepositoryTest {
    
    @Autowired
    ContatoRepository repository;

    @Test
    public void testeCrudContato() {
        Contato contato = Contato.builder()
            .nome("teste")
            .url_logo("teste")
            .build();

        //Create
        Contato salvo = repository.save(contato);

        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<Contato> pesquisado = repository.findById(contato.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(contato.getUrl_logo(), pesquisado.get().getUrl_logo());
        Assertions.assertEquals(contato.getNome(), pesquisado.get().getNome());

        //Delete
        repository.delete(contato);
    }
}
