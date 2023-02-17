package com.jardessouza.desafio.funcionario.controller;

import com.jardessouza.desafio.funcionario.dto.FuncionarioPatchRequest;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gerenciadorfuncionarios/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO save(@RequestBody FuncionarioRequestDTO request){
        return this.funcionarioService.save(request);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioResponseDTO findByIdFuncionario(@PathVariable Long id){
        return this.funcionarioService.findByIdFuncionario(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionarioResponseDTO> listAllFuncionarios(){
        return this.funcionarioService.getAllFuncionarios();
    }

    @GetMapping(path = "/cep/{cep}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioResponseDTO getFuncionarioByCep(@PathVariable String cep){
        return this.funcionarioService.getFuncionarioByCep(cep);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFuncionario(@PathVariable Long id, @RequestBody FuncionarioRequestDTO request){
        this.funcionarioService.updateFuncionario(id, request);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFuncionario(@PathVariable Long id){
        this.funcionarioService.deleteFuncionario(id);
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void replaceFuncionario(@PathVariable Long id, @RequestBody FuncionarioPatchRequest request){
        this.funcionarioService.replaceFuncionario(id, request);
    }







}
