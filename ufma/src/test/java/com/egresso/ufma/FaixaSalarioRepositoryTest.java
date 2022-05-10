package com.egresso.ufma;

import java.util.Set;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.FaixaSalarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FaixaSalarioRepositoryTest {
    @Autowired
    FaixaSalarioRepository repository;

    @Test 
    public void deveSalvarFaixaSalario() {
        System.out.println("oi");
    } 
}
