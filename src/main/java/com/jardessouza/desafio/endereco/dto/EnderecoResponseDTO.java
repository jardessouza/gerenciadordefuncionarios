package com.jardessouza.desafio.endereco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {
    private Long id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String uf;
}
