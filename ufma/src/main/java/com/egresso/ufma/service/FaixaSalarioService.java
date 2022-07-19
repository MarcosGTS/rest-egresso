package com.egresso.ufma.service;

import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.dto.EstatisticaDTO;
import com.egresso.ufma.repository.FaixaSalarioRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import java.util.LinkedList;
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

    public List<EstatisticaDTO> obterQuantitavioCompleto() {
        List<FaixaSalario> faixas = obterFaixasSalariais();
        List<EstatisticaDTO> estatisticas = new LinkedList<EstatisticaDTO>();

        for (FaixaSalario faixa : faixas) {
            String label = faixa.getDescricao();
            Integer value = consultarQuantidadeEgressos(faixa.getId());

            EstatisticaDTO novoDado = EstatisticaDTO
            .builder()
            .label(label)
            .value(value)
            .build();

            estatisticas.add(novoDado);
        }

        return estatisticas;
    }
    
    private void verificarExistencia(Long faixaSalrioId) {
        if (! repository.existsById(faixaSalrioId)) {
            throw new RegraNegocioRunTime("Faixa de salario nao existe");
        }
    }
}
