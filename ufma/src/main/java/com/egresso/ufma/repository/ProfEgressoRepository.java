package com.egresso.ufma.repository;

import com.egresso.ufma.model.ProfEgresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfEgressoRepository extends JpaRepository<ProfEgresso, Long>{
    
    @Query( value = "SELECT p FROM ProfEgresso p JOIN FETCH p.faixaSalario WHERE p.id = ?1")
    public ProfEgresso findFaixaSalario(long profEgresso_id);
}
