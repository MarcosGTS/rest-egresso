package com.egresso.ufma.repository;

import com.egresso.ufma.model.Egresso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EgressoRepository extends JpaRepository<Egresso, Long> {
    
}
