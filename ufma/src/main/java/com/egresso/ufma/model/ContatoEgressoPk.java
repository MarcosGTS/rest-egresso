package com.egresso.ufma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContatoEgressoPk implements Serializable{
    @Column(name="egresso_id")
    private Long egresso_id;

    @Column(name="contato_id")
    private Long contato_id;
}
