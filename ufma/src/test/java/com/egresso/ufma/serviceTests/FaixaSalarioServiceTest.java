package com.egresso.ufma.serviceTests;

import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.repository.ProfEgressoRepository;
import com.egresso.ufma.service.FaixaSalarioService;

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
public class FaixaSalarioServiceTest {
    
    @Autowired
    FaixaSalarioService service;

    @Autowired
    FaixaSalarioRepository repository;

    @Autowired
    EgressoRepository egressoRepo;

    @Autowired
    ProfEgressoRepository profEgressoRepo;

    @Test
    public void deveConsultarNumeroEgressos() {
        FaixaSalario novaFaixaSalario = repository.save( FaixaSalario
        .builder()
        .descricao("1000-2000")
        .build());

        Egresso egresso1 = egressoRepo.save(Egresso
        .builder()
        .nome("Marcos")
        .build());

        Egresso egresso2 = egressoRepo.save(Egresso
        .builder()
        .nome("Joriscreid")
        .build());
        
        profEgressoRepo.save(ProfEgresso
        .builder()
        .egresso(egresso1)
        .faixaSalario(novaFaixaSalario)
        .build());

        profEgressoRepo.save(ProfEgresso
        .builder()
        .egresso(egresso2)
        .faixaSalario(novaFaixaSalario)
        .build());

        Integer consulta = service.consultarQuantidadeEgressos(novaFaixaSalario);

        Assertions.assertEquals(2, consulta);
    }
}
