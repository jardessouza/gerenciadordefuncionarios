package com.jardessouza.desafio.funcionario.documentation;

import com.jardessouza.desafio.funcionario.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface FuncionarioControllerDocs {
    @Operation(summary = "Operation to create employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully."),
            @ApiResponse(responseCode = "400", description = "Missing mandatory fields.")
    })
    FuncionarioResponseDTO save(FuncionarioRequestDTO request);


    @Operation(summary = "Operation to find employee by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully."),
            @ApiResponse(responseCode = "404", description = "Employee not found.")
    })
    FuncionarioResponseDTO findByIdFuncionario(FuncionarioIdRequestDTO request);

    @Operation(summary = "Operation to list all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all employees successfully retrieved."),
    })
    List<FuncionarioResponseDTO> listAllFuncionarios();

    @Operation(summary = "Operation to Get Employee by CEP.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully."),
            @ApiResponse(responseCode = "404", description = "Employee not found.")
    })
    FuncionarioResponseDTO getFuncionarioByCep(FuncionarioCepRequestDTO request);

    @Operation(summary = "Operation to edit employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully edited."),
            @ApiResponse(responseCode = "404", description = "Employee not found."),
            @ApiResponse(responseCode = "400", description = "Missing mandatory fields")
    })
    void updateFuncionario(FuncionarioUpdateRequestDTO request);

    @Operation(summary = "Operation to delete employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Employee not found.")
    })
    void deleteFuncionario(FuncionarioIdRequestDTO request);

    @Operation(summary = "Operation edits a single employee attribute.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully edited."),
            @ApiResponse(responseCode = "404", description = "Employee not found."),
            @ApiResponse(responseCode = "400", description = "Missing mandatory fields")
    })
    void replaceFuncionario(FuncionarioPatchRequestDTO request);

}
