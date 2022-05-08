package com.egresso.ufma.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prof_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfEgresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egresso_id")
    private Long id;

    @Column(name = "empresa")
    private String empresa;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "data_registro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_registro;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_faixa_salario")
    private FaixaSalario faixaSalario;
}
