package com.jardessouza.desafio.funcionario.controller;

import com.jardessouza.desafio.funcionario.dto.FuncionarioPatchRequest;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;

import java.util.List;

public interface FuncionarioControllerDocs {
    FuncionarioResponseDTO save(FuncionarioRequestDTO request);
    FuncionarioResponseDTO findByIdFuncionario(Long id);
    List<FuncionarioResponseDTO> listAllFuncionarios();
    FuncionarioResponseDTO getFuncionarioByCep(String cep);
    void updateFuncionario(Long id, FuncionarioRequestDTO request);
    void deleteFuncionario(Long id);
    void replaceFuncionario(Long id, FuncionarioPatchRequest request);
}
