package com.egresso.ufma.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Egresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_egresso")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "url_foto")
    private String url_foto;

    @OneToMany(mappedBy = "egresso")
    private List<Depoimento> depoimentos;
    
    @OneToMany(mappedBy = "egresso")
    private List<ProfEgresso> profissoes;   

    @ManyToMany
    @JoinTable(
        name = "contato_egresso",
        joinColumns = @JoinColumn(name = "egresso_id"),
        inverseJoinColumns = @JoinColumn(name = "contato_id")
    )
    private List<Contato> contatos;

    @OneToMany(mappedBy = "egresso")
    private List<CursoEgresso> cursoEgressoAssoc;
}
