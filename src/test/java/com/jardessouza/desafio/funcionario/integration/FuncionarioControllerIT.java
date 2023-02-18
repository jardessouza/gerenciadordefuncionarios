package com.jardessouza.desafio.funcionario.integration;

import com.jardessouza.desafio.funcionario.builder.FuncionarioDTOBuilder;
import com.jardessouza.desafio.funcionario.dto.FuncionarioIdRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import com.jardessouza.desafio.funcionario.repository.FuncionarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FuncionarioControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void WhenSaveReturnsEmployeeWithSuccess() {
        ResponseEntity<Funcionario> funcionarioEntity = createAndSaveFuncionario();

        Assertions.assertThat(funcionarioEntity).isNotNull();
        Assertions.assertThat(funcionarioEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(funcionarioEntity.getBody()).isNotNull();

    }

    @Test
    void WhenGetSuccessReturnsListOfEmployees() {
        ResponseEntity<Funcionario> funcionarioEntityCreated = createAndSaveFuncionario();

        List<Funcionario> listAllFuncionarios = testRestTemplate.exchange("/api/v1/gerenciadorfuncionarios/funcionarios", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Funcionario>>() {
                }).getBody();

        Assertions.assertThat(listAllFuncionarios).isNotNull();
        Assertions.assertThat(listAllFuncionarios.get(0).getNome()).isEqualTo(funcionarioEntityCreated.getBody().getNome());
    }

    private ResponseEntity<Funcionario> createAndSaveFuncionario() {
        FuncionarioRequestDTO funcionarioRequestDTO = FuncionarioDTOBuilder.builder().build().buildFuncionarioDTO();

        return testRestTemplate.postForEntity(
                "/api/v1/gerenciadorfuncionarios/funcionarios", funcionarioRequestDTO, Funcionario.class);
    }
}
