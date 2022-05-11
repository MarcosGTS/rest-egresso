package com.egresso.ufma.modelTests;

import java.util.Optional;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.repository.FaixaSalarioRepository;

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
public class FaixaSalarioRepositoryTest {
    @Autowired
    FaixaSalarioRepository repository;

    @Test 
    public void testeSalvarFaixaSalario() {
          
        FaixaSalario faixa = FaixaSalario
            .builder()
            .descricao("teste")
            .build();

        //Create
        FaixaSalario salvo = repository.save(faixa);

        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<FaixaSalario> pesquisado = repository.findById(faixa.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(faixa.getDescricao(), pesquisado.get().getDescricao());
    } 

    @Test
    public void testeRemoverFaixaSalario() {
        FaixaSalario faixa = FaixaSalario
            .builder()
            .descricao("teste")
            .build();

        //Create
        FaixaSalario salvo = repository.save(faixa);
        repository.deleteById(salvo.getId());

        Optional<FaixaSalario> pesquisado = repository.findById(salvo.getId());

        Assertions.assertFalse(pesquisado.isPresent());
    }
}
