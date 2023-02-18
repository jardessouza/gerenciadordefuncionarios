package com.jardessouza.desafio.funcionario.builder;

import com.jardessouza.desafio.endereco.builder.EnderecoDTOBuilder;
import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.funcionario.dto.FuncionarioPatchRequest;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import com.jardessouza.desafio.funcionario.enums.Sexo;
import lombok.Builder;

@Builder
public class FuncionarioDTOBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String nome = "Jardes Souza";
    @Builder.Default
    private Integer idade = 25;
    @Builder.Default
    private Sexo sexo = Sexo.MASCULINO;
    @Builder.Default
    private String cep = "65065600";
    @Builder.Default
    private Endereco endereco = EnderecoDTOBuilder.builder().build().buildEndereco();

    public FuncionarioRequestDTO buildFuncionarioDTO(){
        return new FuncionarioRequestDTO(nome, idade, sexo, cep);
    }

    public FuncionarioPatchRequest buildFuncionarioDTOpatch(){
        return new FuncionarioPatchRequest(nome, idade, sexo, cep);
    }

    public FuncionarioResponseDTO buildFuncionarioResponse(){
        return new FuncionarioResponseDTO(id ,nome, idade, sexo, cep , endereco);
    }

    public Funcionario buildFuncionario(){
        return Funcionario.builder()
                .id(id)
                .nome(nome)
                .idade(idade)
                .sexo(sexo)
                .endereco(endereco)
                .build();
    }

}
