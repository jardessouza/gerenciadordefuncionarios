package com.jardessouza.desafio.endereco.service;

import com.jardessouza.desafio.endereco.dto.EnderecoResponseDTO;
import com.jardessouza.desafio.endereco.feign.EnderecoFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoFeign enderecoFeign;
    public EnderecoResponseDTO getEndereco(String cep){
        return this.enderecoFeign.buscarEnderecoCep(cep);
    }

}
