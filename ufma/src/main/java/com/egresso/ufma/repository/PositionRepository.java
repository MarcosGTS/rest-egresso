package com.egresso.ufma.repository;

import com.egresso.ufma.model.Posicao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Posicao, Long>{
    
}
