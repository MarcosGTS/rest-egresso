package com.egresso.ufma.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoEgressoPk implements Serializable {
    @Column(name = "curso_id")
    private Long curso_id;

    @Column(name = "egresso_id")
    private Long egresso_id;
}
