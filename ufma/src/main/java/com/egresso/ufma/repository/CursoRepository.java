package com.egresso.ufma.repository;

import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    // @Query(value="SELECT e FROM Egresso e JOIN e.cursoEgressoAssoc assoc WHERE assoc.id.curso_id = ?1")
    // public List<Egresso> getEgressos(Long curso_id);

    @Query(value="SELECT COUNT(c) FROM Curso c JOIN c.cursoEgressoAssoc WHERE c.id = ?1")
    public Integer quatidadeEgressos(Long curso_id);

    @Query(value="SELECT c FROM Curso c LEFT JOIN FETCH c.cursoEgressoAssoc WHERE c.id = ?1")
    public Curso findCompleteCurso(Long id);
}
