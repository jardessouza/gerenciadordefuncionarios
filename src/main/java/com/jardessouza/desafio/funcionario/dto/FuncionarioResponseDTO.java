package com.jardessouza.desafio.funcionario.dto;

import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDTO {
    private Long id;
    private String nome;
    private Integer idade;
    private Sexo sexo;
}
