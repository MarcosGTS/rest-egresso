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

    @OneToMany(mappedBy = "egresso")
    private List<ContatoEgresso> contatos;

    @OneToMany(mappedBy = "egresso")
    private List<CursoEgresso> cursoEgressoAssoc;
}
