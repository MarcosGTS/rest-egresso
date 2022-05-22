package com.egresso.ufma.service;

import java.time.LocalDate;
import java.util.List;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.repository.DepoimentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepoimentoService {

    @Autowired 
    DepoimentoRepository repository;
    
    public Depoimento salvar(Depoimento depoimento) {
        //validar depoimento
        return repository.save(depoimento);
    }
    
    public void remover(Depoimento depoimento) {
        //check if exists
        repository.deleteById(depoimento.getId());
    }

    public Depoimento editar(Depoimento depoimento, String text) {
        depoimento.setTexto(text);
        depoimento.setData(LocalDate.now());
        return depoimento;
    }

    public List<Depoimento> consultar() {
        return repository.consultarDepoimentos();
    }

    public List<Depoimento> consultarPorEgresso(Egresso egresso) {
        return repository.findByEgresso(egresso.getId());
    }
}
