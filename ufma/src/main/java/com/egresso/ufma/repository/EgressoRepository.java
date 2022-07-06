package com.egresso.ufma.repository;

import java.util.LinkedList;
import java.util.Optional;

import com.egresso.ufma.model.ContatoEgresso;
import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.Egresso;
import com.egresso.ufma.model.ProfEgresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EgressoRepository extends JpaRepository<Egresso, Long> {
    public Boolean existsByEmail(String email);
    public Boolean existsByCpf(String cpf);

    Optional<Egresso> findByEmail(String email);

    @Query(value = "SELECT ce FROM ContatoEgresso ce LEFT JOIN FETCH ce.contato c WHERE ce.egresso.id = ?1")
    public LinkedList<ContatoEgresso> findContatos(Long egresso_id);

    @Query(value = "SELECT ce FROM CursoEgresso ce LEFT JOIN FETCH ce.curso WHERE ce.egresso.id = ?1")
    public LinkedList<CursoEgresso> findCursoEgressos(Long egresso_id);

    @Query(value = "SELECT p FROM ProfEgresso p LEFT JOIN FETCH p.cargo WHERE p.egresso.id = ?1")
    public LinkedList<ProfEgresso> findProfissoes(Long egresso_id);

    @Query(value = "SELECT e FROM Egresso e LEFT JOIN FETCH e.contatos WHERE e.id = ?1")
    public Egresso findEgressoComContato(Long id);
}
