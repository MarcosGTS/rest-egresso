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
    public void testeCrudCargo() {
        Cargo cargo = Cargo.builder()
            .nome("teste")
            .descricao("teste")
            .build();

        //Create
        Cargo salvo = repository.save(cargo);

        Assertions.assertNotNull(salvo);
        
        //Recover
        Optional<Cargo> pesquisado = repository.findById(cargo.getId());
        
        Assertions.assertNotNull(pesquisado.get());
        Assertions.assertEquals(cargo.getDescricao(), salvo.getDescricao());
        Assertions.assertEquals(cargo.getNome(), salvo.getNome());

        //Delete
        repository.delete(cargo);
    }
}
