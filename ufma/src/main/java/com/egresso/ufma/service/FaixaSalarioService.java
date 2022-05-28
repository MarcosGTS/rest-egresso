package com.egresso.ufma.service;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.repository.FaixaSalarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaixaSalarioService {
    @Autowired
    FaixaSalarioRepository repository;

    public Integer consultarQuantidadeEgressos(FaixaSalario faixaSalario) {
        //TODO: checar existencia egresso
        return repository.getNumberEgressos(faixaSalario.getId());
    }
}
