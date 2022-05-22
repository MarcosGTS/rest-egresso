package com.egresso.ufma.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faixa_salario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaSalario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faixa_salario")
    private Long id;
    
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "faixaSalario")
    private List<ProfEgresso> profissoes;
}
