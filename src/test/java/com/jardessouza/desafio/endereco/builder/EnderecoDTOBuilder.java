package com.jardessouza.desafio.endereco.builder;

import com.jardessouza.desafio.endereco.dto.EnderecoResponseDTO;
import com.jardessouza.desafio.endereco.entity.Endereco;
import lombok.Builder;


@Builder
public class EnderecoDTOBuilder {
    @Builder.Default
    private String logradouro = "Rua Três";
    @Builder.Default
    private String bairro = "Turu";
    @Builder.Default
    private String uf = "MA";

    public EnderecoResponseDTO buildEnderecoResponse(){
        return new EnderecoResponseDTO(logradouro, bairro, uf);
    }

    public Endereco buildEndereco(){
        return Endereco.builder()
                .logradouro(logradouro)
                .bairro(bairro)
                .uf(uf)
                .build();
    }

}
