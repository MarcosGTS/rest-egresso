package com.egresso.ufma.serviceTests;

import java.util.LinkedList;
import java.util.List;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.CargoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.repository.ProfEgressoRepository;
import com.egresso.ufma.service.CargoService;

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
public class CargoServiceTest {
    @Autowired
    CargoService service;

    @Autowired 
    CargoRepository repository;

    @Autowired
    ProfEgressoRepository profEgressoRepo;

    @Autowired
    EgressoRepository egressoRepo;

    @Test
    public void deveSalvarCargo() {
        
        Cargo novoCargo = Cargo
        .builder()
        .nome("Desenvolvedor")
        .descricao("descricao")
        .build();

        Cargo salvo = service.salvar(novoCargo);

        Assertions.assertTrue(repository.existsById(salvo.getId()));
    }


    @Test
    public void deveDeletarCargo() {
        
        Cargo novoCargo = Cargo
        .builder()
        .nome("Desenvolvedor")
        .descricao("descricao")
        .build();

        Cargo salvo = service.salvar(novoCargo);

        service.deletar(salvo.getId());

        Assertions.assertFalse(repository.existsById(salvo.getId()));
    }


    @Test
    public void deveEditarCargo() {

        Cargo novoCargo = Cargo
        .builder()
        .nome("Desenvolvedor Full-Stack")
        .descricao("descricao")
        .build();

        Cargo salvo = service.salvar(novoCargo);
        
        String novoNome = "Desenvolverdor Mobile";
        String novaDescricao = "Desenvolvimento voltado para sistemas de menor capacidade"; 
        service.editar(salvo.getId(), novoNome, novaDescricao);
        Cargo editado = repository.findById(salvo.getId()).get();
        
        Assertions.assertEquals(novoNome, editado.getNome());
        Assertions.assertEquals(novaDescricao, editado.getDescricao());
    }

    @Test
    public void deveConsultarCargoPorEgresso() {
        
        Cargo novoCargo = service.salvar(Cargo
        .builder()
        .nome("Desenvolvedor Full-Stack")
        .descricao("descricao")
        .build());
        
        Egresso novoEgresso = egressoRepo.save(Egresso
        .builder()
        .nome("nome")
        .build());
        
        ProfEgresso profissao = profEgressoRepo.save(ProfEgresso
        .builder()
        .cargo(novoCargo)
        .egresso(novoEgresso)
        .build());
        
        List<ProfEgresso> profissoes = new LinkedList<ProfEgresso>();
        profissoes.add(profissao);

        novoCargo.setProfissoes(profissoes);
        novoEgresso.setProfissoes(profissoes);

        List<Cargo> cargos = service.consultarCargoPorEgresso(novoEgresso.getId());

        Assertions.assertEquals(1, cargos.size());
        Assertions.assertEquals(novoCargo.getNome(), cargos.get(0).getNome());
    }

    @Test
    public void deveConsultarQuantitativoEgressoPorCargo() {
        
        Cargo novoCargo = service.salvar(Cargo
        .builder()
        .nome("Desenvolvedor Full-Stack")
        .descricao("descricao")
        .build());
        
        Egresso egresso1 = egressoRepo.save(Egresso
        .builder()
        .nome("Joao")
        .build());

        Egresso egresso2 = egressoRepo.save(Egresso
        .builder()
        .nome("Juvenal")
        .build());
        
        profEgressoRepo.save(ProfEgresso
        .builder()
        .cargo(novoCargo)
        .egresso(egresso1)
        .build());

        profEgressoRepo.save(ProfEgresso
        .builder()
        .cargo(novoCargo)
        .egresso(egresso2)
        .build());
        
        Integer consulta = service.consultarQuantidadeEgressos(novoCargo.getId());

        Assertions.assertEquals(2, consulta);
    }

}
