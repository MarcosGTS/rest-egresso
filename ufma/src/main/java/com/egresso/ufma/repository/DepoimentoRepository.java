package com.egresso.ufma.repository;

import java.util.List;

import com.egresso.ufma.model.Depoimento;
import com.egresso.ufma.model.Egresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long>{
    @Query(value="SELECT D FROM Depoimento D WHERE D.egresso.id = ?1")
    public List<Depoimento> findByEgresso(Long egresso_id);
   
    @Query(value="SELECT D FROM Depoimento D ORDER BY D.data DESC")
    public List<Depoimento> consultarDepoimentos();
}
