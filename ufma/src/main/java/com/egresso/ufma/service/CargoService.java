package com.egresso.ufma.service;

import java.util.List;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.repository.CargoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {
    @Autowired
    CargoRepository repository;

    public Cargo salvar(Cargo cargo) {
        verificarCargo(cargo);
        return repository.save(cargo);
    }

    public Cargo editar(Long id, String nome, String descricao) {
        Cargo cargo = repository.findById(id).get();

        if (nome != null) cargo.setNome(nome);
        if (descricao != null) cargo.setDescricao(descricao);
        
        verificarCargo(cargo);
        
        return salvar(cargo);
    }

    public Cargo deletar(Long id) {
        
        if (repository.existsById(id)) {
            Cargo cargo = repository.findById(id).get();
            repository.deleteById(id);
            return cargo;
        } else {
            throw new RegraNegocioRunTime("Cargo escolhido para remocao ausente");
        }
            
    }

    public List<Cargo> consultarCargoPorEgresso(Long id) {
        return repository.getByEgresso(id);
    }

    public Integer consultarQuantidadeEgressos(Long id) {
        Cargo cargo = repository.findById(id).get();
        verificarCargo(cargo);

        return repository.getNumberGraduates(cargo.getId());
    }

    public List<Cargo> obterTodosCargos() {
        return repository.findAll();
    }

    private void verificarCargo(Cargo cargo) {
        if (cargo == null) 
            throw new RegraNegocioRunTime("Um Cargo valido deve ser informado");
        
        if (cargo.getNome() == null || cargo.getNome().isBlank())
            throw new RegraNegocioRunTime("Nome do cargo deve ser informado");

        if (cargo.getDescricao() == null || cargo.getDescricao().isBlank())
            throw new RegraNegocioRunTime("Descricao do cargo deve ser informada");
    }

}
