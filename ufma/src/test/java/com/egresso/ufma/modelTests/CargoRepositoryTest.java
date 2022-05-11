package com.egresso.ufma.modelTests;

import java.util.Optional;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.repository.CargoRepository;

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
public class CargoRepositoryTest {
    @Autowired
    CargoRepository repository;

    @Test
    public void testeSalvarCargo() {
        Cargo cargo = Cargo.builder()
            .nome("teste")
            .descricao("teste")
            .build();

        
        Cargo salvo = repository.save(cargo);
        Assertions.assertNotNull(salvo);
        
        Optional<Cargo> pesquisado = repository.findById(cargo.getId());

        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(cargo.getDescricao(), pesquisado.get().getDescricao());
        Assertions.assertEquals(cargo.getNome(), pesquisado.get().getNome());
    }


    @Test 
    public void testeRemoverCargo() {
        Cargo cargo = Cargo.builder()
            .nome("teste")
            .descricao("teste")
            .build();
        
        Cargo salvo = repository.save(cargo);
        repository.deleteById(salvo.getId());

        Optional<Cargo> pesquisado = repository.findById(salvo.getId());

        Assertions.assertFalse(pesquisado.isPresent());
    }
}
