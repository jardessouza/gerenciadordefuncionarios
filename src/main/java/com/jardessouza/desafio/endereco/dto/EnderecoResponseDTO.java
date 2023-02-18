package com.jardessouza.desafio.endereco.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoResponseDTO {
    private String logradouro;
    private String bairro;
    private String uf;
}
