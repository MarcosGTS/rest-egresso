package com.egresso.ufma.modelTests;

import java.time.LocalDate;
import java.util.Optional;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.repository.DepoimentoRepository;

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
public class DepoimentoRepositoryTest {
    
    @Autowired
    private DepoimentoRepository repository;

    @Test
    public void testeCrudDepoimento() {
        Depoimento depoimento = Depoimento.builder()
            .data(LocalDate.now())
            .texto("texto teste")
            .build();

        //Create
        Depoimento salvo = repository.save(depoimento);

        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<Depoimento> pesquisado = repository.findById(depoimento.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(depoimento.getTexto(), pesquisado.get().getTexto());
        Assertions.assertEquals(depoimento.getData(), pesquisado.get().getData());

        //Delete
        repository.delete(depoimento);
    }
}
