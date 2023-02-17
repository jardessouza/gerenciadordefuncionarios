package com.jardessouza.desafio.endereco.feign;

import com.jardessouza.desafio.endereco.dto.EnderecoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface EnderecoFeign {
    @GetMapping("/{cep}/json")
    EnderecoResponseDTO buscarEnderecoCep(@PathVariable("cep") String cep);
}
