package com.egresso.ufma.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Contato;
import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.repository.CargoRepository;
import com.egresso.ufma.repository.ContatoRepository;
import com.egresso.ufma.repository.CursoEgressoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.repository.ProfEgressoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EgressoService {
    
    @Autowired
    EgressoRepository repository;

    @Autowired
    CursoEgressoRepository cursoEgressoRepo;

    @Autowired
    ProfEgressoRepository profEgressoRepo;

    @Autowired
    ContatoRepository contatoRepo;

    @Autowired
    CargoRepository cargoRepo;

    @Autowired
    CursoRepository cursoRepo;

    @Autowired 
    FaixaSalarioRepository faixaSalarioRepo;

    public Egresso salvar(Egresso egresso) {
        verificarEgresso(egresso);
        return repository.save(egresso);
    };

    public void deletar(Long id) {
        Egresso egresso = repository.findById(id).get();

        verificarExistencia(egresso);
        repository.delete(egresso);
    }

    public List<Contato> adicionarContato(Egresso egresso, Contato novoContato) {
        verificarExistencia(egresso);

        if (egresso.getContatos() == null) egresso.setContatos(new LinkedList<Contato>());
        egresso.getContatos().add(novoContato);
        repository.save(egresso);
        
        if (novoContato.getEgressos() == null) novoContato.setEgressos(new LinkedList<Egresso>());
        novoContato.getEgressos().add(egresso);
        contatoRepo.save(novoContato);

        return egresso.getContatos();
    }

    public Contato editarContato(Egresso egresso, Contato contatoEditado, Contato novoContato) {
        verificarExistencia(egresso);

        contatoEditado.getEgressos().remove(egresso);
        egresso.getContatos().remove(contatoEditado);

        adicionarContato(egresso, novoContato);
        
        return novoContato;
    }

    public void adicionarCurso(Egresso egresso, Curso curso, LocalDate dataInicio, LocalDate dataConclusao) {
        verificarExistencia(egresso);

        CursoEgresso novoCursoEgresso = cursoEgressoRepo.save(
            CursoEgresso.builder()
            .id(new CursoEgressoPk(curso.getId(), egresso.getId()))
            .data_inicio(dataInicio)
            .data_conclusao(dataConclusao)
            .curso(curso)
            .egresso(egresso)
            .build());

        if (egresso.getCursoEgressoAssoc() == null) egresso.setCursoEgressoAssoc(new LinkedList<CursoEgresso>());
        egresso.getCursoEgressoAssoc().add(novoCursoEgresso);
        repository.save(egresso);

        if (curso.getCursoEgressoAssoc() == null) curso.setCursoEgressoAssoc(new LinkedList<CursoEgresso>());
        curso.getCursoEgressoAssoc().add(novoCursoEgresso);
        cursoRepo.save(curso);
    }

    public Boolean editarCurso(Egresso egresso, Curso cursoAtual, Curso novoCurso) {
        
        for (CursoEgresso cursoEgresso : egresso.getCursoEgressoAssoc()) {
            if (cursoEgresso.getId().getCurso_id() != cursoAtual.getId())
                continue;

            //Atualiza relacoes entre as tabelas
            cursoAtual.getCursoEgressoAssoc().remove(cursoEgresso);
            cursoEgresso.getId().setCurso_id(novoCurso.getId());

            if (novoCurso.getCursoEgressoAssoc() == null) novoCurso.setCursoEgressoAssoc(new LinkedList<CursoEgresso>()); 
            novoCurso.getCursoEgressoAssoc().add(cursoEgresso);
           
            return true;
        }
        
        return false;
    }

    public void adicionarCargo(Egresso egresso, Cargo cargo, String nomeEmpresa, String descricao, LocalDate dataRegistro) {
        verificarExistencia(egresso);

        ProfEgresso profEgresso = profEgressoRepo.save(
            ProfEgresso.builder()
            .egresso(egresso)
            .cargo(cargo)
            .empresa(nomeEmpresa)
            .descricao(descricao)
            .data_registro(dataRegistro)
            .build());
        
        if (egresso.getProfissoes() == null) egresso.setProfissoes(new LinkedList<ProfEgresso>());
        egresso.getProfissoes().add(profEgresso);
        repository.save(egresso);

        if (cargo.getProfissoes() == null) cargo.setProfissoes(new LinkedList<ProfEgresso>());
        cargo.getProfissoes().add(profEgresso);
        cargoRepo.save(cargo);

    }

    public Boolean editarCargo(Egresso egresso, Cargo cargoAtual, Cargo novoCargo) {
        verificarExistencia(egresso);

        for (ProfEgresso profEgresso : egresso.getProfissoes()) {
            if (profEgresso.getCargo().getId() != cargoAtual.getId()) 
                continue;

            //Atualiza relacoes entre as tabelas
            cargoAtual.getProfissoes().remove(profEgresso);
            profEgresso.setCargo(novoCargo);

            if (novoCargo.getProfissoes() == null) novoCargo.setProfissoes(new LinkedList<ProfEgresso>());
            novoCargo.getProfissoes().add(profEgresso);

            return true;
        }

        return false;
    }

    public Boolean editarFaixaSalario(Egresso egresso, Cargo cargoAtual, FaixaSalario novaFaixaSalario) {
        verificarExistencia(egresso);

        for (ProfEgresso profEgresso : egresso.getProfissoes()) {
            if (profEgresso.getCargo().getId() != cargoAtual.getId()) 
                continue;

            if (novaFaixaSalario.getProfissoes() == null) novaFaixaSalario.setProfissoes(new LinkedList<ProfEgresso>());
            novaFaixaSalario.getProfissoes().add(profEgresso);
            faixaSalarioRepo.save(novaFaixaSalario);

            profEgresso.setFaixaSalario(novaFaixaSalario);
            profEgressoRepo.save(profEgresso);

            return true;
        }
        
        return false;
    }

    public Optional<Egresso> getEgresso(Long egresso_id) {
        return repository.findById(egresso_id);
    }

    public Egresso getFullEgresso(Long egresso_id) {

        Egresso consulta = repository.findById(egresso_id).get();

        Egresso novoEgresso = Egresso
        .builder()
        .id(consulta.getId())
        .nome(consulta.getNome())
        .cpf(consulta.getCpf())
        .email(consulta.getEmail())
        .resumo(consulta.getResumo())
        .build();

        novoEgresso.setContatos(repository.findContatos(egresso_id));
        novoEgresso.setCursoEgressoAssoc(repository.findCursoEgressos(egresso_id));
        novoEgresso.setProfissoes(repository.findProfissoes(egresso_id));

        for (ProfEgresso profEgresso : novoEgresso.getProfissoes()) {
            ProfEgresso aux = profEgressoRepo.findFaixaSalario(profEgresso.getId());
            if (aux != null)
                profEgresso.setFaixaSalario(aux.getFaixaSalario());
        }

        return novoEgresso;
    }

    private void verificarEgresso(Egresso egresso) {
        if (egresso == null)
            throw new RegraNegocioRunTime("Um Egresso valido deve ser informado");
        if (egresso.getNome() == null || egresso.getNome().isBlank()) 
            throw new RegraNegocioRunTime("Egresso deve possuir um nome");
        if (egresso.getCpf() == null || egresso.getCpf().isBlank()) 
            throw new RegraNegocioRunTime("Egresso deve possuir cpf");
        if (egresso.getEmail() == null || egresso.getEmail().isBlank()) 
            throw new RegraNegocioRunTime("Egresso deve possuir email");

        Boolean existenciaEmail= repository.existsByEmail(egresso.getEmail());
        Boolean existenciaCpf = repository.existsByCpf(egresso.getCpf());

        if (existenciaEmail) 
            throw new RegraNegocioRunTime("Email informado ja cadastrado");
        if (existenciaCpf)
            throw new RegraNegocioRunTime("Cpf informa ja cadastrado");
    }

    private void verificarExistencia(Egresso egresso) {
        if (!repository.existsById(egresso.getId())) {
            throw new RegraNegocioRunTime("Egresso nao existe no banco");
        }
    }
}