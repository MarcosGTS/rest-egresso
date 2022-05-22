package com.egresso.ufma.repository;

import java.util.List;

import com.egresso.ufma.model.Curso;
import com.egresso.ufma.model.Egresso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    @Query()
    public List<Egresso> getEgressos(Long curso_id);

    @Query()
    public Integer getNumberEgressos(Long curso_id);
}
