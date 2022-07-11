package com.egresso.ufma.service;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaixaSalarioService {
    @Autowired
    FaixaSalarioRepository repository;
    public List<FaixaSalario> obterFaixasSalariais() {
        return repository.findAll();
    }
    
    public Integer consultarQuantidadeEgressos(Long faixaSalarioId) {
        verificarExistencia(faixaSalarioId);
        return repository.getNumberEgressos(faixaSalarioId);
    }
    
    private void verificarExistencia(Long faixaSalrioId) {
        if (! repository.existsById(faixaSalrioId)) {
            throw new RegraNegocioRunTime("Faixa de salario nao existe");
        }
    }
}
