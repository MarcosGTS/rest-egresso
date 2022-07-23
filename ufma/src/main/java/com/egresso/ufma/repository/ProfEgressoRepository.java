package com.egresso.ufma.repository;

import java.lang.Long;
import com.egresso.ufma.model.Cargo;
import com.egresso.ufma.model.FaixaSalario;
import com.egresso.ufma.model.ProfEgresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfEgressoRepository extends JpaRepository<ProfEgresso, Long>{
    
    @Query( value = "SELECT p FROM ProfEgresso p JOIN FETCH p.faixaSalario WHERE p.id = ?1")
    public ProfEgresso findFaixaSalario(long profEgresso_id);

    @Query( value = "SELECT p.cargo.id FROM ProfEgresso p WHERE p.id = ?1")
    public Long findCargo(Long profegresso_id);

    @Query( value = "SELECT p.faixaSalario.id FROM ProfEgresso p WHERE p.id = ?1")
    public Long findFaixa(Long profEgresso_id);

}
