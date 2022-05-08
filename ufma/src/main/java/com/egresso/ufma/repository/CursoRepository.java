package com.egresso.ufma.repository;

import com.egresso.ufma.model.Curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
