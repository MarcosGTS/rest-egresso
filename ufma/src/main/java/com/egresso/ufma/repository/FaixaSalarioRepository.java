package com.egresso.ufma.repository;

import com.egresso.ufma.model.FaixaSalario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaixaSalarioRepository extends JpaRepository<FaixaSalario, Long> {
    @Query(value="SELECT COUNT(e) FROM Egresso e JOIN e.profissoes p WHERE p.faixaSalario.id = ?1")
    public Integer getNumberEgressos(Long faixaSalario_id);

}
