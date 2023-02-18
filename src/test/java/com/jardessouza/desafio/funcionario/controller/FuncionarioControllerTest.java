package com.jardessouza.desafio.funcionario.controller;

import com.jardessouza.desafio.endereco.builder.EnderecoDTOBuilder;
import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.endereco.repository.EnderecoRepository;
import com.jardessouza.desafio.endereco.service.EnderecoService;
import com.jardessouza.desafio.funcionario.builder.FuncionarioDTOBuilder;
import com.jardessouza.desafio.funcionario.dto.FuncionarioPatchRequest;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class FuncionarioControllerTest {
    @InjectMocks
    private FuncionarioController funcionarioController;
    @Mock
    private FuncionarioService funcionarioServiceMock;

    @Mock
    private EnderecoService enderecoServiceMock;

    @Mock
    private EnderecoRepository enderecoRepositoryMock;

    private FuncionarioDTOBuilder funcionarioDTOBuilder;

    private EnderecoDTOBuilder enderecoDTOBuilder;
    @BeforeEach
    void setUp(){
        funcionarioDTOBuilder = FuncionarioDTOBuilder.builder().build();
        enderecoDTOBuilder = EnderecoDTOBuilder.builder().build();

        BDDMockito.when(this.funcionarioServiceMock.save(ArgumentMatchers.any()))
                .thenReturn(funcionarioDTOBuilder.buildFuncionarioResponse());

        BDDMockito.when(this.funcionarioServiceMock.findByIdFuncionario(ArgumentMatchers.anyLong()))
                .thenReturn(funcionarioDTOBuilder.buildFuncionarioResponse());

        BDDMockito.when(this.funcionarioServiceMock.getAllFuncionarios())
                .thenReturn(List.of(funcionarioDTOBuilder.buildFuncionarioResponse()));

        BDDMockito.when(this.funcionarioServiceMock.getFuncionarioByCep(ArgumentMatchers.anyString()))
                        .thenReturn(funcionarioDTOBuilder.buildFuncionarioResponse());

        BDDMockito.when(this.enderecoServiceMock.getEndereco(ArgumentMatchers.anyString()))
                .thenReturn(enderecoDTOBuilder.buildEnderecoResponse());


        BDDMockito.when(this.enderecoRepositoryMock.save(ArgumentMatchers.any(Endereco.class)))
                .thenReturn(enderecoDTOBuilder.buildEndereco());

        BDDMockito.doNothing().when(this.funcionarioServiceMock).deleteFuncionario(ArgumentMatchers.anyLong());

    }

    @Test
    void WhenSaveAndSuccessReturnsEmployee(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        FuncionarioResponseDTO funcionarioCreated =
                this.funcionarioController.save(funcionarioDTOBuilder.buildFuncionarioDTO());

        Assertions.assertThat(funcionarioCreated.getId()).isNotNull();
        Assertions.assertThat(funcionarioCreated.getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenSearchForIdEGetSuccessRetornarEmployee(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        FuncionarioResponseDTO funcionarioFound = this.funcionarioController.findByIdFuncionario(1L);

        Assertions.assertThat(funcionarioFound.getId()).isNotNull();
        Assertions.assertThat(funcionarioFound.getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenGetSuccessReturnsListOfEmployees(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        List<FuncionarioResponseDTO> funcionariosList = this.funcionarioController.listAllFuncionarios();

        Assertions.assertThat(funcionariosList)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(funcionariosList.get(0).getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenFindCEPReturnsEmployeeWithSuccess(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();
        FuncionarioResponseDTO funcionarioFound = this.funcionarioController.getFuncionarioByCep("65065600");

        Assertions.assertThat(funcionarioFound.getId()).isNotNull();
        Assertions.assertThat(funcionarioFound.getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenSuccessUpdateEmployee(){
        FuncionarioRequestDTO funciarioToBeUpdate = funcionarioDTOBuilder.buildFuncionarioDTO();
        funciarioToBeUpdate.setNome("Bruno");
        Assertions.assertThatCode(() -> this.funcionarioController.updateFuncionario(
                1L, funciarioToBeUpdate)).doesNotThrowAnyException();
    }

    @Test
    void WhenSuccessDeleteEmployee(){
        Assertions.assertThatCode(() -> this.funcionarioController.deleteFuncionario(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void WhenGetSuccessUpdateAttribute(){
        FuncionarioPatchRequest funcionarioToBeCreated = funcionarioDTOBuilder.buildFuncionarioDTOpatch();
        funcionarioToBeCreated.setNome("Julio");
        Assertions.assertThatCode(() -> this.funcionarioController.replaceFuncionario(
                1L,funcionarioToBeCreated)).doesNotThrowAnyException();

    }

}
