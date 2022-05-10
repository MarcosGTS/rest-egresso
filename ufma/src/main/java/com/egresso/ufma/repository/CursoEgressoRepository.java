package com.egresso.ufma.repository;

import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoEgressoRepository extends JpaRepository <CursoEgresso, CursoEgressoPk> {
    
}
