package com.egresso.ufma.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "curso_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoEgresso {
    @EmbeddedId
    private CursoEgressoPk id;

    @Column(name = "data_inicio")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_inicio;

    @Column(name = "data_conclusao")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_conclusao;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;
    
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
}

