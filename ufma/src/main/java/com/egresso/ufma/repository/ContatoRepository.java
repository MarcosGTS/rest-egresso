package com.egresso.ufma.repository;

import com.egresso.ufma.model.Contato;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long>{
    
}
