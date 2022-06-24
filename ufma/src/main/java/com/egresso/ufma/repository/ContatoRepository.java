package com.egresso.ufma.repository;

import com.egresso.ufma.model.Contato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContatoRepository extends JpaRepository<Contato, Long>{
    
    @Query(value = "SELECT c FROM Contato c LEFT JOIN FETCH c.egressos WHERE c.id = ?1")
    public Contato findCompleteContato(Long id);
}
