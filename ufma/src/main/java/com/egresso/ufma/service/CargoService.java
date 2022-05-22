package com.egresso.ufma.service;

import java.util.List;

import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.CargoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {
    @Autowired
    CargoRepository repository;

    public Cargo salvar(Cargo cargo) {
        //checar se cargo e valido
        return repository.save(cargo);
    }

    public Cargo editar(Cargo cargo, String nome, String descricao) {
        //checar inputs 
        cargo.setNome(nome);
        cargo.setDescricao(descricao);
        
        return cargo;
    }

    public void deletar(Cargo cargo) {
        //checar se exite

        repository.delete(cargo);
    }

    public List<Cargo> consultarCargoPorEgresso(Egresso egresso) {
        //checar se egresso existe

        return repository.getByEgresso(egresso.getId());
    }

    public Integer consultarQuantidadeEgressos(Cargo cargo) {
        // checar se cargo existe

        return repository.getNumberGraduates(cargo.getId());
    }
}
