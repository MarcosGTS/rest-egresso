package com.egresso.ufma.service;

import java.time.LocalDate;
import java.util.List;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.DepoimentoRepository;
import com.egresso.ufma.service.exceptions.RegraNegocioRunTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepoimentoService {

    @Autowired 
    DepoimentoRepository repository;
    
    public Depoimento salvar(Depoimento depoimento) {
        verificarDepoimento(depoimento);
        return repository.save(depoimento);
    }
    
    public void remover(Depoimento depoimento) {
        verificarExistencia(depoimento);
        repository.deleteById(depoimento.getId());
    }

    public Depoimento editar(Depoimento depoimento, String text) {
        verificarExistencia(depoimento);
        depoimento.setTexto(text);
        depoimento.setData(LocalDate.now());
        verificarDepoimento(depoimento);

        return depoimento;
    }

    public List<Depoimento> consultar() {
        return repository.consultarDepoimentos();
    }

    public List<Depoimento> consultarPorEgresso(Egresso egresso) {
        return repository.findByEgresso(egresso.getId());
    }

    private void verificarDepoimento(Depoimento depoimento) {
        if (depoimento == null)
            throw new RegraNegocioRunTime("Depoimento valido deve ser informado");
        if (depoimento.getTexto() == null || depoimento.getTexto().isBlank()) 
            throw new RegraNegocioRunTime("Texto nao pode estar vazio");
        if (depoimento.getData() == null)
            throw new RegraNegocioRunTime("Depoimento deve possuir data de postagem");
    }

    private void verificarExistencia(Depoimento depoimento) {
        if (!repository.existsById(depoimento.getId())) {
            throw new RegraNegocioRunTime("Depoimento nao existe");
        }
    }
}
