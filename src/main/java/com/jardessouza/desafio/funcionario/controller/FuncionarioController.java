package com.jardessouza.desafio.funcionario.controller;

import com.jardessouza.desafio.funcionario.documentation.FuncionarioControllerDocs;
import com.jardessouza.desafio.funcionario.dto.*;
import com.jardessouza.desafio.funcionario.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gerenciadorfuncionarios/api/v1/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController implements FuncionarioControllerDocs {
    private final FuncionarioService funcionarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO save(@RequestBody @Valid FuncionarioRequestDTO request) {
        return this.funcionarioService.save(request);
    }

    @PostMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioResponseDTO findByIdFuncionario(@RequestBody @Valid FuncionarioIdRequestDTO request) {
        return this.funcionarioService.findByIdFuncionario(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionarioResponseDTO> listAllFuncionarios() {
        return this.funcionarioService.getAllFuncionarios();
    }

    @PostMapping(path = "/cep")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioResponseDTO getFuncionarioByCep(@RequestBody @Valid FuncionarioCepRequestDTO request) {
        return this.funcionarioService.getFuncionarioByCep(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFuncionario(@RequestBody @Valid FuncionarioUpdateRequestDTO request) {
        this.funcionarioService.updateFuncionario(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFuncionario(@RequestBody @Valid FuncionarioIdRequestDTO request) {
        this.funcionarioService.deleteFuncionario(request);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void replaceFuncionario(@RequestBody FuncionarioPatchRequestDTO request) {
         this.funcionarioService.replaceFuncionario(request);
    }


}
