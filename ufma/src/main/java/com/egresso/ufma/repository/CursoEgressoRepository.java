package com.egresso.ufma.repository;

import com.egresso.ufma.model.CursoEgresso;
import com.egresso.ufma.model.CursoEgressoPk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoEgressoRepository extends JpaRepository <CursoEgresso, CursoEgressoPk> {

    @Query(value = "SELECT ce FROM CursoEgresso ce")
    public List<CursoEgresso> obterTodasRelacoes();
}
