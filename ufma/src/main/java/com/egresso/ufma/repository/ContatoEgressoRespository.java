package com.egresso.ufma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egresso.ufma.model.ContatoEgresso;
import com.egresso.ufma.model.ContatoEgressoPk;

public interface ContatoEgressoRespository extends JpaRepository<ContatoEgresso, ContatoEgressoPk>{
    
}
