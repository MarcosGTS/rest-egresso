package com.egresso.ufma.service;

import java.util.List;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Egresso;
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

    public Cargo editar(Cargo cargo, String nome, String descricao) {
        verificarCargo(cargo);
        cargo.setNome(nome);
        cargo.setDescricao(descricao);
        verificarCargo(cargo);
        
        return salvar(cargo);
    }

    public void deletar(Cargo cargo) {
       
        if (repository.existsById(cargo.getId())) {
            repository.delete(cargo);
        } else {
            throw new RegraNegocioRunTime("Cargo escolhido para remocao ausente");
        }
            
    }

    public List<Cargo> consultarCargoPorEgresso(Egresso egresso) {
        return repository.getByEgresso(egresso.getId());
    }

    public Integer consultarQuantidadeEgressos(Cargo cargo) {
        verificarCargo(cargo);

        return repository.getNumberGraduates(cargo.getId());
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
