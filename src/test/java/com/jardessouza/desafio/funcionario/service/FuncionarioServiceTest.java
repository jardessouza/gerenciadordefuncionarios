package com.jardessouza.desafio.funcionario.service;

import com.jardessouza.desafio.endereco.builder.EnderecoDTOBuilder;
import com.jardessouza.desafio.endereco.entity.Endereco;
import com.jardessouza.desafio.endereco.repository.EnderecoRepository;
import com.jardessouza.desafio.endereco.service.EnderecoService;
import com.jardessouza.desafio.funcionario.builder.FuncionarioDTOBuilder;
import com.jardessouza.desafio.funcionario.dto.*;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import com.jardessouza.desafio.funcionario.repository.FuncionarioRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;
    @Mock
    private FuncionarioRepository funcionarioRepositoryMock;

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

        BDDMockito.when(this.funcionarioRepositoryMock.save(ArgumentMatchers.any(Funcionario.class)))
                .thenReturn(funcionarioDTOBuilder.buildFuncionario());

        BDDMockito.when(this.funcionarioRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(funcionarioDTOBuilder.buildFuncionario()));

        BDDMockito.when(this.funcionarioRepositoryMock.findAll())
                .thenReturn(List.of(funcionarioDTOBuilder.buildFuncionario()));

        BDDMockito.when(this.funcionarioRepositoryMock.findByCep(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(funcionarioDTOBuilder.buildFuncionario()));

        BDDMockito.when(this.enderecoServiceMock.getEndereco(ArgumentMatchers.anyString()))
                .thenReturn(enderecoDTOBuilder.buildEnderecoResponse());


        BDDMockito.when(this.enderecoRepositoryMock.save(ArgumentMatchers.any(Endereco.class)))
                        .thenReturn(enderecoDTOBuilder.buildEndereco());

        BDDMockito.doNothing().when(this.funcionarioRepositoryMock).delete(ArgumentMatchers.any());

    }

    @Test
    void WhenSaveAndSuccessReturnsEmployee(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        FuncionarioResponseDTO funcionarioCreated =
        this.funcionarioService.save(funcionarioDTOBuilder.buildFuncionarioDTO());

        Assertions.assertThat(funcionarioCreated.getId()).isNotNull();
        Assertions.assertThat(funcionarioCreated.getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenSearchForIdEGetSuccessRetornarEmployee(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        FuncionarioResponseDTO funcionarioFound = this.funcionarioService.findAndCheckFuncionarioExists(1L);

        Assertions.assertThat(funcionarioFound.getId()).isNotNull();
        Assertions.assertThat(funcionarioFound.getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenSearchingForIdAndNotSucceededReturnException(){
        BDDMockito.when(this.funcionarioRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> this.funcionarioService.findAndCheckFuncionarioExists(1L));
    }

    @Test
    void WhenGetSuccessReturnsListOfEmployees(){
        FuncionarioRequestDTO funcionarioExpected = funcionarioDTOBuilder.buildFuncionarioDTO();

        List<FuncionarioResponseDTO> funcionariosList = this.funcionarioService.getAllFuncionarios();

        Assertions.assertThat(funcionariosList)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(funcionariosList.get(0).getNome()).isEqualTo(funcionarioExpected.getNome());
    }

    @Test
    void WhenFindCEPReturnsEmployeeWithSuccess(){
        FuncionarioCepRequestDTO funcionarioCepRequestDTO = funcionarioDTOBuilder.buildFuncionarioCepRequest();
        FuncionarioResponseDTO funcionarioFound = this.funcionarioService.getFuncionarioByCep(funcionarioCepRequestDTO);

        Assertions.assertThat(funcionarioFound.getId()).isNotNull();
        Assertions.assertThat(funcionarioFound.getNome()).isEqualTo(funcionarioDTOBuilder.buildFuncionario().getNome());
    }

    @Test
    void WhenSearchingForCEPAndNotSucceededReturnException(){
        BDDMockito.when(this.funcionarioRepositoryMock.findByCep(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        FuncionarioCepRequestDTO funcionarioCepRequestDTO = funcionarioDTOBuilder.buildFuncionarioCepRequest();

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> this.funcionarioService.getFuncionarioByCep(funcionarioCepRequestDTO));
    }

    @Test
    void WhenSuccessUpdateEmployee(){
        FuncionarioUpdateRequestDTO funciarioToBeUpdate = funcionarioDTOBuilder.buildFuncionarioUpdateRequest();
        funciarioToBeUpdate.setNome("Bruno");
        Assertions.assertThatCode(() -> this.funcionarioService.updateFuncionario(
                funciarioToBeUpdate)).doesNotThrowAnyException();
    }

    @Test
    void WhenSuccessDeleteEmployee(){
        FuncionarioIdRequestDTO funcionarioIdRequest = funcionarioDTOBuilder.buildeFuncionarioIdRequest();

        Assertions.assertThatCode(() -> this.funcionarioService.deleteFuncionario(funcionarioIdRequest))
                .doesNotThrowAnyException();
    }

    @Test
    void WhenGetSuccessUpdateAttribute(){
        FuncionarioPatchRequestDTO funcionarioToBeCreated = funcionarioDTOBuilder.buildFuncionarioDTOpatch();
        funcionarioToBeCreated.setNome("Julio");
        Assertions.assertThatCode(() -> this.funcionarioService.replaceFuncionario(
                funcionarioToBeCreated)).doesNotThrowAnyException();

    }

}
