package com.egresso.ufma.service;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaixaSalarioService {
    @Autowired
    FaixaSalarioRepository repository;

    public Integer consultarQuantidadeEgressos(Long faixaSalarioId) {
        return repository.getNumberEgressos(faixaSalarioId);
    }
    
    private void verificarExistencia(FaixaSalario faixaSalario) {
        if (! repository.existsById(faixaSalario.getId())) {
            throw new RegraNegocioRunTime("Faixa de salario nao existe");
        }
    }
}
