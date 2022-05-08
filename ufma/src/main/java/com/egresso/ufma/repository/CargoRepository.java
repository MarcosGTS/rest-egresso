package com.egresso.ufma.repository;

import com.egresso.ufma.model.Cargo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
}
