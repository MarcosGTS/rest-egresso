package com.egresso.ufma.repository;

import com.egresso.ufma.model.Investimento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestimentRepository extends JpaRepository <Investimento, Long>{
    
}
