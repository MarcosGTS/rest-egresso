package com.egresso.ufma.serviceTests;

import java.time.LocalDate;
import java.util.List;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.DepoimentoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.service.DepoimentoService;

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
public class DepoimentoServiceTest {
   
    @Autowired
    DepoimentoService service;

    @Autowired 
    DepoimentoRepository repository;

    @Autowired
    EgressoRepository egressoRepo;

    @Test
    public void deveSalvarDepoimento() {
        Depoimento depoimentoTest = Depoimento
        .builder()
        .texto("texto de teste")
        .data(LocalDate.now())
        .build(); 

        Depoimento salvo = service.salvar(depoimentoTest);

        Assertions.assertNotNull(salvo);
        Assertions.assertEquals(depoimentoTest.getTexto(), salvo.getTexto());
    }

    @Test
    public void deveRemoverDepoimento() {
        Depoimento depoimento1 = Depoimento
        .builder()
        .texto("texto de teste")
        .data(LocalDate.now())
        .build(); 

        Depoimento salvo = service.salvar(depoimento1);
        service.remover(salvo.getId());

        Assertions.assertFalse(repository.existsById(salvo.getId()));
    }

    @Test
    public void deveConsultarDepoimentosOrdenados() {
        Depoimento depoimento1 = Depoimento
        .builder()
        .texto("texto de teste")
        .data(LocalDate.of(2000, 3, 1))
        .build(); 

        Depoimento depoimento2 = Depoimento
        .builder()
        .texto("texto de teste")
        .data(LocalDate.of(2001, 3, 1))
        .build(); 

        service.salvar(depoimento1);
        service.salvar(depoimento2);

        List<Depoimento> consulta = service.consultar();

        Assertions.assertFalse(consulta.get(0).getData().isBefore(consulta.get(1).getData()));

    }

    @Test
    public void deveAtualizarInformacoesDepoimento() {
        Depoimento depoimento1 = Depoimento
        .builder()
        .texto("texto de teste")
        .data(LocalDate.of(2002, 3, 1))
        .build(); 

        Depoimento salvo = service.salvar(depoimento1);

        String novoTexto = "novo Depoimento";
        service.editar(salvo.getId(), novoTexto);

        Assertions.assertEquals(salvo.getTexto(), novoTexto);
        Assertions.assertNotEquals(salvo.getData(), LocalDate.of(2000, 3, 1));
    }

    @Test
    public void deveConsultarDepoimentosEgresso() {
        Egresso egresso = Egresso
        .builder()
        .build();

        Egresso salvo = egressoRepo.save(egresso);

        Depoimento depoimento1 = Depoimento
        .builder()
        .egresso(salvo)
        .texto("texto de teste1")
        .data(LocalDate.of(2003, 3, 1))
        .build(); 

        Depoimento depoimento2 = Depoimento
        .builder()
        .egresso(salvo)
        .texto("texto de teste2")
        .data(LocalDate.now())
        .build(); 

        Depoimento depoimento3 = Depoimento
        .builder()
        .texto("texto de teste3")
        .data(LocalDate.now())
        .build();

        service.salvar(depoimento1);
        service.salvar(depoimento2);
        service.salvar(depoimento3);

        List<Depoimento> depoimentos = service.consultarPorEgresso(salvo.getId());

        Assertions.assertEquals(2, depoimentos.size());
    }
}

