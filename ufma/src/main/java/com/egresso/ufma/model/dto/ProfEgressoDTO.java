package com.egresso.ufma.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfEgressoDTO {
    private String nomeEmpresa;
    private String descricao;
    private String dataRegistro;
    private Long faixaSalarioId;
}
