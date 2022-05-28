package com.egresso.ufma.repository;

import java.util.LinkedList;

import com.egresso.ufma.model.Contato;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.ProfEgresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EgressoRepository extends JpaRepository<Egresso, Long> {
    public Boolean existsByEmail(String email);
    public Boolean existsByCpf(String cpf);

    @Query(value = "SELECT c FROM Contato c JOIN FETCH c.egressos e WHERE e.id = ?1")
    public LinkedList<Contato> findContatos(Long egresso_id);

    @Query(value = "SELECT ce FROM CursoEgresso ce JOIN FETCH ce.curso WHERE ce.egresso.id = ?1")
    public LinkedList<CursoEgresso> findCursoEgressos(Long egresso_id);

    @Query(value = "SELECT p FROM ProfEgresso p JOIN FETCH p.cargo WHERE p.egresso.id = ?1")
    public LinkedList<ProfEgresso> findProfissoes(Long egresso_id);
}
