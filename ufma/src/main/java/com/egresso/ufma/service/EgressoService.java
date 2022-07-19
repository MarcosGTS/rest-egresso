package com.egresso.ufma.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Contato;
import com.egresso.ufma.model.ContatoEgresso;
import com.egresso.ufma.model.ContatoEgressoPk;
import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;
import com.egresso.ufma.model.dto.EgressoDTO;
import com.egresso.ufma.model.dto.ProfEgressoDTO;
import com.egresso.ufma.repository.CargoRepository;
import com.egresso.ufma.repository.ContatoEgressoRespository;
import com.egresso.ufma.repository.ContatoRepository;
import com.egresso.ufma.repository.CursoEgressoRepository;
import com.egresso.ufma.repository.CursoRepository;
import com.egresso.ufma.repository.EgressoRepository;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.repository.ProfEgressoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EgressoService implements UserDetailsService {
    
    @Autowired
    EgressoRepository repository;

    @Autowired
    CursoEgressoRepository cursoEgressoRepo;

    @Autowired
    ProfEgressoRepository profEgressoRepo;

    @Autowired
    ContatoRepository contatoRepo;

    @Autowired
    ContatoEgressoRespository contatoEgressoRepo;

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

    public void deletar(Long egressoId) {
        verificarExistencia(egressoId);

        Egresso egresso = repository.findById(egressoId).get();
        repository.delete(egresso);
    }

    public Egresso editar(Long egressoId, EgressoDTO novoEgresso) {
        verificarExistencia(egressoId);
        Egresso egresso = repository.findById(egressoId).get();
        
        if (novoEgresso.getNome() != null && !novoEgresso.getNome().isBlank())
            egresso.setNome(novoEgresso.getNome());
        
        if (novoEgresso.getCpf() != null && !novoEgresso.getCpf().isBlank())
            egresso.setCpf(novoEgresso.getCpf());
        
        if (novoEgresso.getEmail() != null && !novoEgresso.getEmail().isBlank())
            egresso.setEmail(novoEgresso.getEmail());
        
        if (novoEgresso.getResumo() != null && !novoEgresso.getResumo().isBlank())   
            egresso.setResumo(novoEgresso.getResumo());
        
        if (novoEgresso.getUrl_foto() != null && !novoEgresso.getUrl_foto().isBlank())
            egresso.setUrl_foto(novoEgresso.getUrl_foto());

        return repository.save(egresso);
    }

    public ContatoEgresso adicionarContato(Long egressoId, Long contatoId, String endereco) {
        
        Egresso egresso = getFullEgresso(egressoId);
        Contato contato = contatoRepo.findCompleteContato(contatoId);

        //validacoes
        verificarExistencia(egressoId);

        ContatoEgresso novoContato = contatoEgressoRepo.save(
            ContatoEgresso.builder()
            .id(new ContatoEgressoPk(egressoId, contatoId))
            .egresso(egresso)
            .contato(contato)
            .endereco(endereco)
            .build()
        );
        
        if (egresso.getContatos() == null) egresso.setContatos(new LinkedList<ContatoEgresso>()); 
        egresso.getContatos().add(novoContato);
        repository.save(egresso);
        

        if (contato.getEgressos() == null) contato.setEgressos(new LinkedList<ContatoEgresso>());
        contato.getEgressos().add(novoContato);
        contatoRepo.save(contato);

        return novoContato;
    }

    public ContatoEgresso editarContato(Long egressoId, Long contatoId, Long novoContatoId, String endereco) {
        Egresso egresso = repository.findById(egressoId).get();
        Contato contato = contatoRepo.findById(contatoId).get();
        Contato novoContato = contatoRepo.findById(novoContatoId).get();

        ContatoEgressoPk contatoEgressoK = new ContatoEgressoPk(egressoId, contatoId);
        ContatoEgresso contatoEgresso = contatoEgressoRepo.findById(contatoEgressoK).get();

        verificarExistencia(egressoId);

        if (endereco != null) 
            contatoEgresso.setEndereco(endereco);
        
        if (novoContato != null) {
            contatoEgresso.setContato(novoContato);
            contato.getEgressos().remove(contatoEgresso);
        }
        
        return contatoEgresso;
    }

    public Curso adicionarCurso(Long egressoId, Long cursoId, LocalDate dataInicio, LocalDate dataConclusao) {
        Egresso egresso = getFullEgresso(egressoId);
        Curso curso = cursoRepo.findCompleteCurso(cursoId);

        verificarExistencia(egressoId);

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

        return curso;
    }

    public Curso editarCurso(Long egressoId, Long cursoAtualId, Long novoCursoId, LocalDate dataInicio, LocalDate dataConclusao) {
        Egresso egresso = getFullEgresso(egressoId);
        Curso cursoAtual = cursoRepo.findCompleteCurso(cursoAtualId);
        Curso novoCurso = cursoRepo.findCompleteCurso(novoCursoId);

        for (CursoEgresso cursoEgresso : egresso.getCursoEgressoAssoc()) {
            if (cursoEgresso.getId().getCurso_id() != cursoAtual.getId())
                continue;

            //Atualiza relacoes entre as tabelas
            if(novoCursoId != null) {
                cursoAtual.getCursoEgressoAssoc().remove(cursoEgresso);
                cursoEgresso.getId().setCurso_id(novoCurso.getId());
    
                if (novoCurso.getCursoEgressoAssoc() == null) novoCurso.setCursoEgressoAssoc(new LinkedList<CursoEgresso>()); 
                novoCurso.getCursoEgressoAssoc().add(cursoEgresso);
            }
            
            if (dataInicio != null) cursoEgresso.setData_inicio(dataInicio);
            if (dataConclusao != null) cursoEgresso.setData_conclusao(dataConclusao);

            return novoCurso;
        }
        
        return null;
    }

    public Cargo adicionarCargo(Long egressoId, Long cargoId, ProfEgressoDTO novoCargo) {
        verificarExistencia(egressoId);
        
        Egresso egresso = getFullEgresso(egressoId);
        Cargo cargo = cargoRepo.findCompleteCargo(cargoId);
        FaixaSalario faixaSalario = faixaSalarioRepo.findCompleteFaixaSalario(novoCargo.getFaixaSalarioId());

        LocalDate dataRegistro = LocalDate.parse(novoCargo.getDataRegistro());

        ProfEgresso profEgresso = profEgressoRepo.save(
            ProfEgresso.builder()
            .egresso(egresso)
            .cargo(cargo)
            .empresa(novoCargo.getNomeEmpresa())
            .descricao(novoCargo.getDescricao())
            .data_registro(dataRegistro)
            .faixaSalario(faixaSalario)
            .build());
        
        if (egresso.getProfissoes() == null) egresso.setProfissoes(new LinkedList<ProfEgresso>());
        egresso.getProfissoes().add(profEgresso);
        repository.save(egresso);

        if (cargo.getProfissoes() == null) cargo.setProfissoes(new LinkedList<ProfEgresso>());
        cargo.getProfissoes().add(profEgresso);
        cargoRepo.save(cargo);

        if (faixaSalario.getProfissoes() == null) faixaSalario.setProfissoes(new LinkedList<ProfEgresso>());
        faixaSalario.getProfissoes().add(profEgresso);
        faixaSalarioRepo.save(faixaSalario);

        return cargoRepo.save(cargo);
    }

    public Cargo editarCargo(Long egressoId, Long cargoAtualId, Long novoCargoId, ProfEgressoDTO dto) {
        Egresso egresso = getFullEgresso(egressoId);
        Cargo cargoAtual = cargoRepo.findCompleteCargo(cargoAtualId);
        Cargo novoCargo = cargoRepo.findCompleteCargo(novoCargoId);

        verificarExistencia(egressoId);

        for (ProfEgresso profEgresso : egresso.getProfissoes()) {
            if (profEgresso.getCargo().getId() != cargoAtual.getId()) 
                continue;

            //Atualiza relacoes entre as tabelas
            cargoAtual.getProfissoes().remove(profEgresso);
            profEgresso.setCargo(novoCargo);

            if (novoCargo.getProfissoes() == null) novoCargo.setProfissoes(new LinkedList<ProfEgresso>());
            novoCargo.getProfissoes().add(profEgresso);

            //TODO: Validar data
            String novoNome = dto.getNomeEmpresa();
            String novaDescricao = dto.getDescricao();
            LocalDate novaDataRegistro = LocalDate.parse(dto.getDataRegistro());

            if (novoNome != null) profEgresso.setEmpresa(novoNome);
            if (novaDescricao != null) profEgresso.setDescricao(novaDescricao);
            if (novaDataRegistro != null) profEgresso.setData_registro(novaDataRegistro);;

            return novoCargo;
        }

        return null;
    }

    public FaixaSalario editarFaixaSalario(Long egressoId, Long cargoId, Long faixaSalarioId) {
        Egresso egresso = getFullEgresso(egressoId);
        Cargo cargoAtual = cargoRepo.findCompleteCargo(cargoId);
        FaixaSalario novaFaixaSalario = faixaSalarioRepo.findCompleteFaixaSalario(faixaSalarioId);
        
        verificarExistencia(egressoId);

        for (ProfEgresso profEgresso : egresso.getProfissoes()) {
            if (profEgresso.getCargo().getId() != cargoAtual.getId()) 
                continue;

            if (novaFaixaSalario.getProfissoes() == null) novaFaixaSalario.setProfissoes(new LinkedList<ProfEgresso>());
            novaFaixaSalario.getProfissoes().add(profEgresso);
            faixaSalarioRepo.save(novaFaixaSalario);

            profEgresso.setFaixaSalario(novaFaixaSalario);
            profEgressoRepo.save(profEgresso);

            return novaFaixaSalario;
        }
        
        return null;
    }

    public Optional<Egresso> getEgresso(Long egresso_id) {
        return repository.findById(egresso_id);
    }

    @Transactional
    public Egresso getFullEgresso(Long egresso_id) {

        Egresso consulta = repository.findById(egresso_id).get();

        Egresso novoEgresso = Egresso
            .builder()
            .id(consulta.getId())
            .nome(consulta.getNome())
            .cpf(consulta.getCpf())
            .email(consulta.getEmail())
            .resumo(consulta.getResumo())
            .url_foto(consulta.getUrl_foto())
            .senha(consulta.getSenha())
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

    public List<Egresso> obterTodosEgressos() {
        List<Egresso> egressos = repository.findAll();
        List<Egresso> egressosCompletos = new ArrayList<Egresso>();

        for (Egresso egresso : egressos) {
            Egresso egressoCompleto = getFullEgresso(egresso.getId());
            egressosCompletos.add(egressoCompleto);
        }

        return egressosCompletos;
    }

    @Override
    public UserDetails loadUserByUsername(String email) 
        throws UsernameNotFoundException {
        Optional<Egresso> usr = repository.findByEmail(email);
        if (!usr.isPresent())            
            throw new UsernameNotFoundException(email);
        
        Egresso egresso = usr.get();
        List<GrantedAuthority> roles = new ArrayList<>();

        return new User(egresso.getEmail(), egresso.getSenha(), roles);
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
        if (egresso.getSenha() == null || egresso.getSenha().isBlank()) 
            throw new RegraNegocioRunTime("Egresso deve possuir senha");
        if (egresso.getSenha().length() < 6) 
            throw new RegraNegocioRunTime("Senha deve ter mais de 5 caracteres");

        Boolean existenciaEmail= repository.existsByEmail(egresso.getEmail());
        Boolean existenciaCpf = repository.existsByCpf(egresso.getCpf());

        if (existenciaEmail) 
            throw new RegraNegocioRunTime("Email informado ja cadastrado");
        if (existenciaCpf)
            throw new RegraNegocioRunTime("Cpf informa ja cadastrado");
    }

    private void verificarExistencia(Long egressoId) {
        if (!repository.existsById(egressoId)) {
            throw new RegraNegocioRunTime("Egresso nao existe no banco");
        }
    }
}