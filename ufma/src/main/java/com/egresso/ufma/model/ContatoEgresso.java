package com.egresso.ufma.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contato_egresso")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContatoEgresso {
    
    @EmbeddedId
    private ContatoEgressoPk id;

    @Column(name="endereco")
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_contato")
    private Contato contato;
}
