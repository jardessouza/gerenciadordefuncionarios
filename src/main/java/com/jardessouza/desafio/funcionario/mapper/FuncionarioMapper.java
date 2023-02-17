package com.jardessouza.desafio.funcionario.mapper;

import com.jardessouza.desafio.funcionario.dto.FuncionarioPatchRequest;
import com.jardessouza.desafio.funcionario.dto.FuncionarioRequestDTO;
import com.jardessouza.desafio.funcionario.dto.FuncionarioResponseDTO;
import com.jardessouza.desafio.funcionario.entity.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    public static final FuncionarioMapper INSTANCE = Mappers.getMapper(FuncionarioMapper.class);

    Funcionario toModel(FuncionarioRequestDTO funcionarioRequestDTO);
    Funcionario toModel(FuncionarioResponseDTO funcionarioResponseDTO);
    List<FuncionarioResponseDTO> toDTO(List<Funcionario> funcionarioList);
    FuncionarioResponseDTO toDTO(Funcionario funcionario);
    FuncionarioRequestDTO toDTORequest(Funcionario funcionario);
    Funcionario toModel(FuncionarioPatchRequest funcionarioPatchRequest);

}
