package com.jardessouza.desafio.funcionario.dto;

import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioPatchRequestDTO {
    private Long id;
    private String nome;
    private Integer idade;
    private Sexo sexo;
    private String cep;
}
