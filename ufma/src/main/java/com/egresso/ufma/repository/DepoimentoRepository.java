package com.egresso.ufma.repository;

import com.egresso.ufma.model.Depoimento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long>{
    
}
