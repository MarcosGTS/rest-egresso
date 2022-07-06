package com.egresso.ufma.serviceTests;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Contato;
import com.egresso.ufma.model.ContatoEgresso;
import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.CargoRepository;
import com.egresso.ufma.repository.ContatoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.service.EgressoService;

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
public class EgressoServiceTest {
    
    @Autowired
    EgressoService service;

    @Autowired
    EgressoRepository repository;

    @Autowired
    CargoRepository cargoRepo;

    @Autowired
    FaixaSalarioRepository faixaSalarioRepo;

    @Autowired
    CursoRepository cursoRepo;

    @Autowired 
    ContatoRepository contatoRepo;

    @Test
    public void deveAdicionarEgresso() {
        Egresso novoEgresso = criarEgressoExemplo();

        Egresso consulta = service.getEgresso(novoEgresso.getId()).get();

        Assertions.assertNotNull(consulta);
        Assertions.assertEquals(novoEgresso.getNome(), consulta.getNome());
        Assertions.assertEquals(novoEgresso.getEmail(), consulta.getEmail());
        Assertions.assertEquals(novoEgresso.getCpf(), consulta.getCpf());

    }

    @Test
    public void deveDeletarEgresso() {
        Egresso novoEgresso = criarEgressoExemplo();
        
        service.deletar(novoEgresso.getId());
        Optional<Egresso> consulta = service.getEgresso(novoEgresso.getId());

        Assertions.assertFalse(consulta.isPresent());
    }

    @Test 
    public void deveAdicionarCargoAoEgresso() {
        Egresso egresso = criarEgressoExemplo();
        Cargo cargo = criarCargoExemplo();

        String nomeEmpresa = "Empresa Teste";
        String descricaoEmpresa = "Descricao teste";
        LocalDate dataRegistro = LocalDate.now();

        service.adicionarCargo(egresso.getId(), cargo.getId(), nomeEmpresa, descricaoEmpresa, dataRegistro);
        
        Egresso consulta = service.getFullEgresso(egresso.getId());
        Cargo cargoConsulta = consulta.getProfissoes().get(0).getCargo();

        Assertions.assertNotNull(consulta);
        Assertions.assertEquals(cargo.getId(), cargoConsulta.getId());
        Assertions.assertEquals(cargo.getDescricao(), cargoConsulta.getDescricao());

    }

    @Test
    public void deveAdicionarCursoAoEgresso() {
        Egresso egresso = criarEgressoExemplo();
        Curso curso = criarCursoExemplo();

        LocalDate dataInicio = LocalDate.of(2001, 03, 27);
        LocalDate dataConclusao = LocalDate.now();

        service.adicionarCurso(egresso.getId(), curso.getId(), dataInicio, dataConclusao);

        Egresso consulta = service.getFullEgresso(egresso.getId());
        CursoEgresso cursoEgresso =  consulta.getCursoEgressoAssoc().get(0);
        Curso cursoConsulta = cursoEgresso.getCurso();

        Assertions.assertNotNull(consulta);
        Assertions.assertNotNull(cursoConsulta);
        Assertions.assertEquals(curso.getId(), cursoConsulta.getId());
        Assertions.assertEquals(curso.getNome(), cursoConsulta.getNome());
        Assertions.assertEquals(curso.getNivel(), cursoConsulta.getNivel());
        Assertions.assertEquals(dataInicio, cursoEgresso.getData_inicio());
        Assertions.assertEquals(dataConclusao, cursoEgresso.getData_conclusao());

    }

    @Test 
    public void deveAdicionarContatoAoEgresso() {
        Egresso novoEgresso = criarEgressoExemplo();
        Contato novoContato = criarContatoExemplo();

        String endereco = "https://teste.com";
        service.adicionarContato(novoEgresso.getId(), novoContato.getId(), endereco);

        Egresso consulta = service.getFullEgresso(novoEgresso.getId());

        Assertions.assertNotNull(consulta);
        Assertions.assertNotNull(consulta.getContatos());
        Assertions.assertNotEquals(0, consulta.getContatos().size());

        ContatoEgresso consultaContato = consulta.getContatos().get(0);
        Assertions.assertEquals(novoContato.getId(), consultaContato.getId().getEgresso_id());
        Assertions.assertEquals(novoContato.getNome(), consultaContato.getContato().getNome());
        
        Assertions.assertNotNull(consultaContato.getEgresso());
        Assertions.assertEquals(novoEgresso.getId(), consultaContato.getEgresso().getId());

    }

    //TODO: DeveEditarCurso
    //TODO: DeveEditarCargo
    //TODO: DeveEditarContato

    @Test
    public void deveEditarFaixaSalarioApartirDoEgresso() {
        Egresso novoEgresso = criarEgressoExemplo();
        Cargo novoCargo = criarCargoExemplo();
        FaixaSalario novaFaixaSalario = criarFaixaSalarioExemplo();

        String nomeEmpresa = "Teste";
        String descricao = "descricao teste";
        LocalDate dataRegistro = LocalDate.now();

        service.adicionarCargo(novoEgresso.getId(), novoCargo.getId(), nomeEmpresa, descricao, dataRegistro);
        service.editarFaixaSalario(novoEgresso.getId(), novoCargo.getId(), novaFaixaSalario.getId());

        Egresso consulta = service.getFullEgresso(novoEgresso.getId());
        FaixaSalario consultaFaixaSalario = faixaSalarioRepo.getById(novaFaixaSalario.getId());

        Assertions.assertNotNull(consulta);
        Assertions.assertNotNull(consulta.getProfissoes());
        Assertions.assertNotEquals(0, consulta.getProfissoes().size());
        
        ProfEgresso consultaProfissao = consulta.getProfissoes().get(0);

        Assertions.assertNotNull(consultaProfissao);
        Assertions.assertNotNull(consultaFaixaSalario);
        Assertions.assertEquals(novoCargo.getId(), consultaProfissao.getCargo().getId());
        Assertions.assertNotNull(consultaProfissao.getFaixaSalario());
        Assertions.assertEquals(novaFaixaSalario.getId(), consultaProfissao.getFaixaSalario().getId());

    }

    @Test
    public void deveConsultarEgresso() {
        Egresso novoEgresso = criarEgressoExemplo();
        Cargo novoCargo = criarCargoExemplo();
        Contato novoContato = criarContatoExemplo();
        Curso novoCurso = criarCursoExemplo();
        FaixaSalario novaFaixaSalario = criarFaixaSalarioExemplo();

        String nomeEmpresa = "Emgresa Teste";
        String descricaoEmpresa = "teste descricao";
        LocalDate dataRegistroEmpresa = LocalDate.now(); 

        LocalDate dataInicio = LocalDate.of(2001, 03, 27);
        LocalDate dataConclusao = LocalDate.now();

        service.adicionarCargo(novoEgresso.getId(), novoCargo.getId(), nomeEmpresa, descricaoEmpresa, dataRegistroEmpresa);
        service.editarFaixaSalario(novoEgresso.getId(), novoCargo.getId(), novaFaixaSalario.getId());
        service.adicionarCurso(novoEgresso.getId(), novoCurso.getId(), dataInicio, dataConclusao);
        
        String endereco = "https://teste.com";
        service.adicionarContato(novoEgresso.getId(), novoContato.getId(), endereco);

        Egresso consulta = service.getFullEgresso(novoEgresso.getId());

        Assertions.assertNotNull(consulta);
        Assertions.assertEquals(novoEgresso.getNome(), consulta.getNome());
        Assertions.assertEquals(novoContato.getId(), consulta.getContatos().get(0).getId().getContato_id());
        Assertions.assertEquals(novoCurso.getId(), consulta.getCursoEgressoAssoc().get(0).getCurso().getId());
        Assertions.assertEquals(novoCargo.getId(), consulta.getProfissoes().get(0).getCargo().getId());    
        Assertions.assertEquals(1, consulta.getProfissoes().size()); 
        Assertions.assertNotNull(consulta.getProfissoes().get(0).getFaixaSalario());
        Assertions.assertEquals(novaFaixaSalario.getDescricao(), consulta.getProfissoes().get(0).getFaixaSalario().getDescricao());

    }

    private Egresso criarEgressoExemplo() {
        Egresso novoEgresso = service.salvar(Egresso
        .builder()
        .nome("Marcos")
        .email("teste@teste.com")
        .cpf("111.111.111-11")
        .senha("1234")
        .contatos(new LinkedList<ContatoEgresso>())
        .cursoEgressoAssoc(new LinkedList<CursoEgresso>())
        .profissoes(new LinkedList<ProfEgresso>())
        .build());
        
        return novoEgresso;
    }

    private Cargo criarCargoExemplo() {
        Cargo cargo = cargoRepo.save(Cargo
        .builder()
        .nome("Teste")
        .descricao("descricao Teste")
        .build());

        return cargo;

    }

    private Curso criarCursoExemplo() {
        Curso curso = cursoRepo.save(Curso
        .builder()
        .nome("nome curso")
        .nivel("Mestrado")
        .build());

        return curso;
    }

    private Contato criarContatoExemplo() {
        Contato contato = contatoRepo.save(Contato
        .builder()
        .nome("nome Contato")
        .url_logo("url img")
        .egressos(new LinkedList<ContatoEgresso>())
        .build());

        return contato;
    }

    private FaixaSalario criarFaixaSalarioExemplo() {
        FaixaSalario faixaSalario = faixaSalarioRepo.save(FaixaSalario
        .builder()
        .descricao("2000-3000")
        .build());

        return faixaSalario;
    }
}
