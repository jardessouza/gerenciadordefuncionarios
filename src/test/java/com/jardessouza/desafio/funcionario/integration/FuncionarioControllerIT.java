package com.jardessouza.desafio.funcionario.integration;

import com.jardessouza.desafio.funcionario.builder.FuncionarioDTOBuilder;
import com.jardessouza.desafio.funcionario.dto.*;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import com.jardessouza.desafio.funcionario.repository.FuncionarioRepository;
import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FuncionarioControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    String url = "/gerenciadorfuncionarios/api/v1/funcionarios";

    @Test
    void WhenSaveReturnsEmployeeWithSuccess() {
        ResponseEntity<Funcionario> funcionarioEntity = createAndSaveFuncionario();

        Assertions.assertThat(funcionarioEntity).isNotNull();
        Assertions.assertThat(funcionarioEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(funcionarioEntity.getBody()).isNotNull();
    }

    @Test
    void WhenFindEmployeeByIdReturnsStatusOkWithSuccess() {
        createAndSaveFuncionario();
        FuncionarioIdRequestDTO request = FuncionarioDTOBuilder.builder().build().buildeFuncionarioIdRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FuncionarioIdRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<FuncionarioResponseDTO> exchange =
                testRestTemplate.exchange(url + "/id", HttpMethod.POST, entity, FuncionarioResponseDTO.class);

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void WhenGetSuccessReturnsListOfEmployees() {
        ResponseEntity<Funcionario> funcionarioEntityCreated = createAndSaveFuncionario();

        List<Funcionario> listAllFuncionarios = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Funcionario>>() {
                }).getBody();

        Assertions.assertThat(listAllFuncionarios).isNotNull();
        Assertions.assertThat(listAllFuncionarios.get(0).getNome()).isEqualTo(funcionarioEntityCreated.getBody().getNome());
    }

    @Test
    void WhenFindEmployeeByCepReturnsStatusOkWithSuccess() {
        createAndSaveFuncionario();
        FuncionarioCepRequestDTO request = FuncionarioDTOBuilder.builder().build().buildFuncionarioCepRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FuncionarioCepRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<FuncionarioResponseDTO> exchange =
                testRestTemplate.exchange(url + "/cep", HttpMethod.POST, entity, FuncionarioResponseDTO.class);

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void WhenUpdateReturnsStatusNoContentWithSuccess() {
        createAndSaveFuncionario();
        FuncionarioUpdateRequestDTO request = FuncionarioDTOBuilder.builder().build().buildFuncionarioUpdateRequest();
        request.setNome("Teste");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FuncionarioUpdateRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> exchange = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void WhenDeleteReturnStatusNoContentWithSuccess() {
        createAndSaveFuncionario();
        FuncionarioIdRequestDTO request = FuncionarioDTOBuilder.builder().build().buildeFuncionarioIdRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FuncionarioIdRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> exchange = testRestTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    private ResponseEntity<Funcionario> createAndSaveFuncionario() {
        FuncionarioRequestDTO funcionarioRequestDTO = FuncionarioDTOBuilder.builder().build().buildFuncionarioDTO();

        return testRestTemplate.postForEntity(
                url, funcionarioRequestDTO, Funcionario.class);
    }
}
