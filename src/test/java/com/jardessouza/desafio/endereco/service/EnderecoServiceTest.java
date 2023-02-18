package com.jardessouza.desafio.endereco.service;

import com.jardessouza.desafio.endereco.builder.EnderecoDTOBuilder;
import com.jardessouza.desafio.endereco.dto.EnderecoResponseDTO;
import com.jardessouza.desafio.endereco.feign.EnderecoFeign;
import feign.FeignException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoFeign enderecoFeign;

    private EnderecoDTOBuilder enderecoDTOBuilder;

    @BeforeEach
    void setUp() {
        enderecoDTOBuilder = EnderecoDTOBuilder.builder().build();

        BDDMockito.when(this.enderecoFeign.buscarEnderecoCep(ArgumentMatchers.anyString()))
                .thenReturn(enderecoDTOBuilder.buildEnderecoResponse());
    }

    @Test
    void WhenObtainSuccessSearchAndReturnAddress() {
        EnderecoResponseDTO endereco = this.enderecoService.getEndereco("65065600");

        Assertions.assertThat(endereco).isNotNull();
        Assertions.assertThat(endereco.getBairro()).isNotNull();
    }

    @Test
    void WhenCepNotForFoundReturnsException(){
        BDDMockito.when(this.enderecoFeign.buscarEnderecoCep(ArgumentMatchers.anyString()))
                        .thenThrow(FeignException.class);

        Assertions.assertThatExceptionOfType(FeignException.class)
                .isThrownBy(() -> this.enderecoService.getEndereco("5556667733344"));
    }
}
