package com.egresso.ufma.repository;

import java.util.List;

import com.egresso.ufma.model.Cargo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CargoRepository extends JpaRepository<Cargo, Long> {
    @Query(value="SELECT c FROM Cargo c join c.profissoes p join p.egresso e WHERE e.id = ?1")
    public List<Cargo> getByEgresso(Long egresso_id);

    @Query(value="SELECT COUNT(e) FROM Egresso e join e.profissoes p WHERE p.cargo.id = ?1")
    public Integer getNumberGraduates(Long cargo_id);

    @Query(value="SELECT c FROM Cargo c LEFT JOIN FETCH c.profissoes WHERE c.id = ?1")
    public Cargo findCompleteCargo(Long id);
}
