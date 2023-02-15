package com.jardessouza.desafio.funcionario.dto;

import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioRequestDTO {
    @NotBlank
    private String nome;
    @NotNull
    private Integer idade;
    @NotNull
    private Sexo sexo;
}
